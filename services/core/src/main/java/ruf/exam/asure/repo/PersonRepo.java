package ruf.exam.asure.repo;

import ruf.exam.asure.entity.Person;
import ruf.exam.asure.entity.Account;
import ruf.exam.asure.enums.Role;

import javax.transaction.Transactional;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class PersonRepo implements PanacheRepositoryBase<Person, Integer> {

    @Transactional
    public Long store(Account acc, String name, String detail) {
        Person p = new Person().setAccount(acc)
                    .setName(name)
                    .setDetail(detail);
        persist(p);
        return p.getId();
    }
}

