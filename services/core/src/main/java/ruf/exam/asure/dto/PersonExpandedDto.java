package ruf.exam.asure.dto;

import ruf.exam.asure.enums.Role;

public class PersonExpandedDto {
    
    private Long id;
    private String name;
    private String detail;
    private String username;
    private String role;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public PersonExpandedDto copy(Object[] flat) {
        id = (Long) flat[0];
        name = (String) flat[1];
        detail = (String) flat[2];
        username = (String) flat[3];
        role = (String) flat[4];
        return this;
    }
}

