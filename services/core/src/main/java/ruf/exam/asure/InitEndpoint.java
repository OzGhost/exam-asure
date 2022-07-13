package ruf.exam.asure;

import ruf.exam.asure.entity.SystemConfiguration;
import ruf.exam.asure.repo.SystemConfigurationRepo;
import ruf.exam.asure.repo.AccountRepo;
import ruf.exam.asure.enums.Role;
import ruf.exam.asure.service.EncryptionService;

import java.util.Arrays;
import java.util.Random;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/init")
public class InitEndpoint {


    @ConfigProperty(name="init.code") String initCode;
    @Inject SystemConfigurationRepo syscfgRepo;
    @Inject AccountRepo accRepo;
    @Inject EncryptionService encryptSv;

    @GET
    @Path("/{code}")
    public Response initBy(@PathParam("code") String code) {
        if ( ! code.equals(initCode)) {
            return Response.status(403).build();
        }
        String salt = syscfgRepo.getValueBy(SystemConfiguration.SALT_KEY);
        if (salt != null) {
            return Response.status(409).build();
        }
        salt = generateSalt();
        syscfgRepo.store(SystemConfiguration.SALT_KEY, salt);
        String pass = encryptSv.encrypt(code, salt);
        accRepo.store("admin", pass, Role.ADMIN);
        return Response.ok().build();
    }

    private String generateSalt() {
        Random r = new Random();
        int len = r.nextInt(30) + 20;
        char[] buf = new char[len];
        for (int i = 0; i < len; i++) {
            switch(r.nextInt(3)) {
                case 0:
                    buf[i] = (char)(r.nextInt(10) + '0'); break;
                case 1:
                    buf[i] = (char)(r.nextInt(26) + 'A'); break;
                default:
                    buf[i] = (char)(r.nextInt(26) + 'a'); break;
            }
        }
        return new String(buf);
    }
}

