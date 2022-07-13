package ruf.exam.asure.repo;

import ruf.exam.asure.entity.Account;
import ruf.exam.asure.enums.Role;

import javax.transaction.Transactional;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class AccountRepo implements PanacheRepositoryBase<Account, String> {

    public Account getBy(String username) {
        return findById(username);
    }

    @Transactional
    public Account store(String username, String password, Role role) {
        Account c = new Account().setUsername(username)
                            .setPassword(password)
                            .setRole(role);
        persist(c);
        return c;
    }
}

