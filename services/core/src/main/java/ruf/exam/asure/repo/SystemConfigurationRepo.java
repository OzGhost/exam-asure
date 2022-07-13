package ruf.exam.asure.repo;

import ruf.exam.asure.entity.SystemConfiguration;
import javax.transaction.Transactional;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class SystemConfigurationRepo implements PanacheRepositoryBase<SystemConfiguration, String> {

    public String getValueBy(String key) {
        SystemConfiguration c = findById(key);
        if (c == null) return null;
        return c.getValue();
    }

    @Transactional
    public void store(String key, String val) {
        SystemConfiguration c = new SystemConfiguration().setKey(key).setValue(val);
        persist(c);
    }
}

