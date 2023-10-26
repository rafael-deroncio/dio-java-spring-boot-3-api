package com.bank.core.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_pix")
@SequenceGenerator(name = "seq_cod_pix", sequenceName = "seq_cod_pix", initialValue = 999, allocationSize = 1)
public class PixModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_pix")
    @Column(name = "cod_pix")
    private Integer id;

    @Column(name = "cod_bak")
    private Integer codBank;

    @Column(name = "cod_agency")
    private Integer codAgency;

    @Column(name = "cod_account")
    private Integer codAccount;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "pix", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PixDetailModel> pixDetails;

    @OneToMany(mappedBy = "pix", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PixTransactionModel> pixTransactions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodBank() {
        return codBank;
    }

    public void setCodBank(Integer codBank) {
        this.codBank = codBank;
    }

    public Integer getCodAgency() {
        return codAgency;
    }

    public void setCodAgency(Integer codAgency) {
        this.codAgency = codAgency;
    }

    public Integer getCodAccount() {
        return codAccount;
    }

    public void setCodAccount(Integer codAccount) {
        this.codAccount = codAccount;
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

    public List<PixDetailModel> getPixDetails() {
        return pixDetails;
    }

    public void setPixDetails(List<PixDetailModel> pixDetails) {
        this.pixDetails = pixDetails;
    }

    public List<PixTransactionModel> getPixTransactions() {
        return pixTransactions;
    }

    public void setPixTransactions(List<PixTransactionModel> pixTransactions) {
        this.pixTransactions = pixTransactions;
    }
}