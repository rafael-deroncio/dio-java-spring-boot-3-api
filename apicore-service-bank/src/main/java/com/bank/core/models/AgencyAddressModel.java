package com.bank.core.models;

import jakarta.persistence.*;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_agency_address")
@SequenceGenerator(name = "seq_cod_agency_address", sequenceName = "seq_cod_agency_address", initialValue = 999, allocationSize = 1)
public class AgencyAddressModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_agency_address")
    @Column(name = "cod_agency_address")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cod_agency", unique = true)
    private AgencyModel agency;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AgencyModel getAgency() {
        return agency;
    }

    public void setAgency(AgencyModel agency) {
        this.agency = agency;
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
