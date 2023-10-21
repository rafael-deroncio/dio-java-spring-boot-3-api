package com.bank.core.models;

import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_client_telephone")
@SequenceGenerator(name = "seq_cod_telephone", sequenceName = "seq_cod_telephone", initialValue = 999, allocationSize = 1)
public class ClientTelephoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_telephone")
    @Column(name = "cod_telephone")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cod_client", referencedColumnName = "cod_client")
    private ClientModel client;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}