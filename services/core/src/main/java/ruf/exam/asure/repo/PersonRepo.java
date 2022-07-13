package ruf.exam.asure.repo;

import ruf.exam.asure.entity.Person;
import ruf.exam.asure.dto.PersonExpandedDto;
import ruf.exam.asure.entity.Account;
import ruf.exam.asure.enums.Role;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    public List<PersonExpandedDto> getAllExpanded() {
        EntityManager em = getEntityManager();
        TypedQuery<Object[]> q = em.createNamedQuery(Person.QRY_GET_ALL_EXPANDED, Object[].class);
        List<Object[]> rs = q.getResultList();
        List<PersonExpandedDto> dtos = new ArrayList<>(rs.size());
        for (Object[] r: rs) {
            dtos.add(new PersonExpandedDto().copy(r));
        }
        return dtos;
    }
}

