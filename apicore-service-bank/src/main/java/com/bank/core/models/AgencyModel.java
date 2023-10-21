package com.bank.core.models;

import jakarta.persistence.*;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_agency")
@SequenceGenerator(name = "seq_cod_agency", sequenceName = "seq_cod_agency", initialValue = 999, allocationSize = 1)
public class AgencyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_agency")
    @Column(name = "cod_agency")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_bank")
    private BankModel bank;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToOne(mappedBy = "agency", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AgencyAddressModel address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BankModel getBank() {
        return bank;
    }

    public void setBank(BankModel bank) {
        this.bank = bank;
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

    public AgencyAddressModel getAddress() {
        return address;
    }

    public void setAddress(AgencyAddressModel address) {
        this.address = address;
    }

}
