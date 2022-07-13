package ruf.exam.asure;

import ruf.exam.asure.entity.Account;
import ruf.exam.asure.entity.Person;
import ruf.exam.asure.repo.AccountRepo;
import ruf.exam.asure.repo.PersonRepo;
import ruf.exam.asure.dto.PersonExpandedDto;
import ruf.exam.asure.service.EncryptionService;
import ruf.exam.asure.enums.Role;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/persons")
public class PersonEndpoint {

    @Inject AccountRepo accRepo;
    @Inject EncryptionService encryptSv;
    @Inject PersonRepo personRepo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(@NotNull @Valid CreatePersonReq rq) {
        // guard(admin)
        Account acc = accRepo.getBy(rq.username);
        if (acc != null) return Response.status(409).build();
        String pass = encryptSv.encrypt(rq.password);
        acc = accRepo.store(rq.username, pass, Role.valueOf(rq.type.name()));
        Long id = personRepo.store(acc, rq.name, rq.detail);
        return Response.ok().entity(id).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons() {
        // guard(#role)
        //List<PersonExpandedDto> persons = personRepo.getAllExpanded();
        List<Person> ps = personRepo.listAll();
        List<PersonExpandedDto> dtos = new ArrayList<>(ps.size());
        for (Person p: ps) {
            Account a = p.getAccount(); // FIXME rebuild to fix n+1 query issue
            PersonExpandedDto dto = new PersonExpandedDto();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setDetail(p.getDetail());
            dto.setUsername(a.getUsername());
            dto.setRole(a.getRole().name());
            dtos.add(dto);
        }
        return Response.ok().entity(dtos).build();
    }


    public static class CreatePersonReq {
        @NotNull private Type type;
        @NotBlank private String name;
        @NotBlank private String username;
        @NotBlank private String password;
        private String detail;

        public Type getType() { return type; }
        public void setType(Type type) { this.type = type; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getDetail() { return detail; }
        public void setDetail(String detail) { this.detail = detail; }
    }
    
    public static enum Type {
        MENTOR,
        STUDENT;
    }
}


