package ruf.exam.asure.service;

import ruf.exam.asure.entity.Session;
import ruf.exam.asure.repo.SessionRepo;
import ruf.exam.asure.enums.Role;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Context;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;

@ApplicationScoped
public class Guard {

    @Inject SessionRepo sessionRepo;

    public Role check(HttpHeaders headers, Role r, Role... rs) {
        List<String> hs = headers.getRequestHeader("autk");
        if (hs == null || hs.isEmpty()) throw new ClientErrorException(401);
        String tk = hs.get(0);
        List<Session> ss = sessionRepo.list("id = ?1 and validFrom <= ?2 and validTill > ?2", tk, new Date());
        if (ss.size() != 1) throw new ClientErrorException(401);
        Role tkRole = ss.get(0).getAccount().getRole();
        if (tkRole == r) return r;
        if (rs != null) {
            for (Role role: rs) {
                if (tkRole == role) return role;
            }
        }
        throw new ForbiddenException();
    }
    
}

