package ruf.exam.asure;

import ruf.exam.asure.entity.Account;
import ruf.exam.asure.entity.Person;
import ruf.exam.asure.repo.AccountRepo;
import ruf.exam.asure.repo.PersonRepo;
import ruf.exam.asure.dto.PersonExpandedDto;
import ruf.exam.asure.service.EncryptionService;
import ruf.exam.asure.service.Guard;
import ruf.exam.asure.enums.Role;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import javax.transaction.Transactional;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Context;

@Path("/persons")
public class PersonEndpoint {

    @Inject AccountRepo accRepo;
    @Inject EncryptionService encryptSv;
    @Inject PersonRepo personRepo;
    @Inject Guard guard;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(@Context HttpHeaders headers, @NotNull @Valid CreatePersonReq rq) {
        guard.check(headers, Role.ADMIN);
        Account acc = accRepo.getBy(rq.username);
        if (acc != null) return Response.status(409).build();
        String pass = encryptSv.encrypt(rq.password);
        acc = accRepo.store(rq.username, pass, Role.valueOf(rq.type.name()));
        Long id = personRepo.store(acc, rq.name, rq.detail);
        return Response.ok().entity(id).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons(@Context HttpHeaders headers) {
        Role r = guard.check(headers, Role.ADMIN, Role.MENTOR, Role.STUDENT);
        switch (r) {
            case ADMIN:
                return fetchAll();
            case MENTOR:
                return fetchByRole(Role.STUDENT);
            default:
                return fetchByRole(Role.MENTOR);
        }
    }

    private Response fetchAll() {
        List<Person> ps = personRepo.listAll();
        List<PersonExpandedDto> dtos = new ArrayList<>(ps.size());
        for (Person p: ps) {
            PersonExpandedDto dto = new PersonExpandedDto().load(p);
            Account a = p.getAccount(); // FIXME rebuild to fix n+1 query issue
            dto.setUsername(a.getUsername());
            dto.setRole(a.getRole().name());
            dtos.add(dto);
        }
        return Response.ok().entity(dtos).build();
    }

    private Response fetchByRole(Role r) {
        List<Person> ps = personRepo.list("account.role", r);
        List<PersonExpandedDto> dtos = new ArrayList<>(ps.size());
        for (Person p: ps) {
            dtos.add(new PersonExpandedDto().load(p));
        }
        return Response.ok().entity(dtos).build();
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePerson(@Context HttpHeaders headers, @PathParam("id") Long id) {
        guard.check(headers, Role.ADMIN);
        Person p = personRepo.findById(id);
        if (p != null) {
            accRepo.delete(p.getAccount());
            personRepo.delete(p);
        }
        return Response.ok().build();
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


