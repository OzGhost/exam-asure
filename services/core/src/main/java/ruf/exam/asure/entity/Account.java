package ruf.exam.asure.entity;

import ruf.exam.asure.enums.Role;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
public class Account {
    
    @Id
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public String getUsername() { return username; }
    public Account setUsername(String username) { this.username = username; return this; }
    public String getPassword() { return password; }
    public Account setPassword(String password) { this.password = password; return this; }
    public Role getRole() { return role; }
    public Account setRole(Role role) { this.role = role; return this; }
}

