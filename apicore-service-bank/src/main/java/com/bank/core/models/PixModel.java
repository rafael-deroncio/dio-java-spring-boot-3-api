package com.bank.core.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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

    @OneToOne
    @JoinColumn(name = "cod_account")
    private AccountModel account;
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

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
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