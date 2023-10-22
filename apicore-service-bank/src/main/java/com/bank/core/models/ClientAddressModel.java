package com.bank.core.models;

import jakarta.persistence.*;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_client_address")
@SequenceGenerator(name = "seq_cod_address", sequenceName = "seq_cod_address", initialValue = 999, allocationSize = 1)
public class ClientAddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_address")
    @Column(name = "cod_address")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "cod_client", referencedColumnName = "cod_client")
    private ClientModel client;
    
    @Column(name = "street", nullable = false)
    private String street;
    
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "state", nullable = false)
    private String state;
    
    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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