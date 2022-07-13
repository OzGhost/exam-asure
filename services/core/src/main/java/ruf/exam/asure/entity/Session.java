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

@Entity
public class Session {
    
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;
    @Column(name = "valid_from")
    private Date validFrom;
    @Column(name = "valid_till")
    private Date validTill;

    public String getId() { return id; }
    public Session setId(String id) { this.id = id; return this; }
    public Account getAccount() { return account; }
    public Session setAccount(Account account) { this.account = account; return this; }
    public Date getValidFrom() { return validFrom; }
    public Session setValidFrom(Date validFrom) { this.validFrom = validFrom; return this; }
    public Date getValidTill() { return validTill; }
    public Session setValidTill(Date validTill) { this.validTill = validTill; return this; }
}

