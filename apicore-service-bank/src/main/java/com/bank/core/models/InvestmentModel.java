package com.bank.core.models;

import java.util.Date;

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

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_investment")
@SequenceGenerator(name = "seq_cod_investment", sequenceName = "seq_cod_investment", initialValue = 999, allocationSize = 1)
public class InvestmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_investment")
    @Column(name = "cod_investment")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cod_account")
    private AccountModel account;

    @Column(name = "name_investment")
    private String nameInvestment;

    @Column(name = "amount", scale = 13, precision = 2)
    private BigDecimal amount;

    @Column(name = "start_date")
    private Date startDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<InvestmentIncomesModel> investmentIncomes;

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

    public String getNameInvestment() {
        return nameInvestment;
    }

    public void setNameInvestment(String nameInvestment) {
        this.nameInvestment = nameInvestment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public List<InvestmentIncomesModel> getInvestmentIncomes() {
        return investmentIncomes;
    }

    public void setInvestmentIncomes(List<InvestmentIncomesModel> investmentIncomes) {
        this.investmentIncomes = investmentIncomes;
    }
}
