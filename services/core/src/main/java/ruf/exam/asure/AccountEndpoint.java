package ruf.exam.asure;

import ruf.exam.asure.entity.Account;
import ruf.exam.asure.repo.AccountRepo;
import ruf.exam.asure.repo.SessionRepo;
import ruf.exam.asure.service.EncryptionService;

import java.util.Arrays;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountEndpoint {

    @Inject AccountRepo accRepo;
    @Inject SessionRepo sessionRepo;
    @Inject EncryptionService encryptSv;

    @POST
    @Path("/tokens")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@NotNull @Valid TokenReq rq) {
        Account acc = accRepo.getBy(rq.username);
        if (acc == null || !acc.getPassword().equals( encryptSv.encrypt(rq.password) )) return Response.status(401).build();
        String sessionId = sessionRepo.createNewSession(acc);
        TokenRs rs = new TokenRs().setToken(sessionId).setRole(acc.getRole().name());
        return Response.status(200).entity(rs).build();
    }

    public static class TokenReq {
        @NotBlank private String username;
        @NotBlank private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class TokenRs {
        private String token;
        private String role;

        public String getToken() { return token; }
        public TokenRs setToken(String token) { this.token = token; return this; }
        public String getRole() { return role; }
        public TokenRs setRole(String role) { this.role = role; return this; }
    }
}

