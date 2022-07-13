package ruf.exam.asure.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="syscfg")
public class SystemConfiguration {

    public static final String SALT_KEY = "passwd.salt";
    
    @Id
    private String key;
    private String value;

    public String getKey() { return key; }
    public SystemConfiguration setKey(String key) { this.key = key; return this; }
    public String getValue() { return value; }
    public SystemConfiguration setValue(String value) { this.value = value; return this; }

}

