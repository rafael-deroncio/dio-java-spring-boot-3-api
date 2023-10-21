package com.bank.core.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_bank")
@SequenceGenerator(name = "seq_cod_bank", sequenceName = "seq_cod_bank", initialValue = 999, allocationSize = 1)
public class BankModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_bank")
    @Column(name = "cod_bank")
    private Integer id;

    @Column(name = "name_bank")
    private String name;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AgencyModel> agencies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<AgencyModel> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<AgencyModel> agencies) {
        this.agencies = agencies;
    }

}