package ruf.exam.asure.repo;

import ruf.exam.asure.entity.Session;
import ruf.exam.asure.entity.Account;
import ruf.exam.asure.enums.Role;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class SessionRepo implements PanacheRepositoryBase<Session, String> {

    public Role getRoleBy(String id) {
        Session s = findById(id);
        if (s == null) return null;
        Account a = s.getAccount();
        if (a == null) return null;
        return a.getRole();
    }

    @Transactional
    public String createNewSession(Account acc) {
        String id = UUID.randomUUID().toString();
        Calendar calen = Calendar.getInstance();
        calen.add(Calendar.MINUTE, 3);
        Date goodUntil = calen.getTime();
        Session s = new Session().setId(id)
                        .setAccount(acc)
                        .setValidFrom(new Date())
                        .setValidTill(goodUntil);
        persist(s);
        return id;
    }
}

