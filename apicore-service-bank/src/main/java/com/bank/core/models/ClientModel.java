package com.bank.core.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_client")
@SequenceGenerator(name = "seq_cod_client", sequenceName = "seq_cod_client", initialValue = 999, allocationSize = 1)
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_client")
    @Column(name = "cod_client")
    private int id;

    @OneToOne
    @JoinColumn(name = "cod_user", referencedColumnName = "cod_user")
    private UserModel user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientAddressModel> addresses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientTelephoneModel> telephones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public List<ClientAddressModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<ClientAddressModel> addresses) {
        this.addresses = addresses;
    }

    public List<ClientTelephoneModel> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<ClientTelephoneModel> telephones) {
        this.telephones = telephones;
    }

}