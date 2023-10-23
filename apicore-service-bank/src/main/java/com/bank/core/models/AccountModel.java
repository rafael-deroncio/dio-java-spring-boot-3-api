package com.bank.core.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_account")
@SequenceGenerator(name = "seq_cod_account", sequenceName = "seq_cod_account", initialValue = 999, allocationSize = 1)
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_account")
    @Column(name = "cod_account")
    private Integer id;

    @Column(name = "cod_client")
    private Integer codClient;

    @Column(name = "cod_agency")
    private Integer codAgency;

    @Column(name = "balance", scale = 2, precision = 13)
    private BigDecimal balance;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountTransactionModel> transactions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodClient() {
        return codClient;
    }

    public void setCodClient(Integer codClient) {
        this.codClient = codClient;
    }

    public Integer getCodAgency() {
        return codAgency;
    }

    public void setCodAgency(Integer codAgency) {
        this.codAgency = codAgency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public List<AccountTransactionModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<AccountTransactionModel> transactions) {
        this.transactions = transactions;
    }

}