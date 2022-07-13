package ruf.exam.asure.entity;

import ruf.exam.asure.enums.Role;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;
    private String name;
    private String detail;

    public Long getId() { return id; }
    public Person setId(Long id) { this.id = id; return this; }
    public Account getAccount() { return account; }
    public Person setAccount(Account account) { this.account = account; return this; }
    public String getName() { return name; }
    public Person setName(String name) { this.name = name; return this; }
    public String getDetail() { return detail; }
    public Person setDetail(String detail) { this.detail = detail; return this; }
}

