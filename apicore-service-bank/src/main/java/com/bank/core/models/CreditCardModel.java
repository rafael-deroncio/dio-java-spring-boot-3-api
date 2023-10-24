package com.bank.core.models;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_credit_card")
@SequenceGenerator(name = "seq_cod_credit_card", sequenceName = "seq_cod_credit_card", initialValue = 999, allocationSize = 1)
public class CreditCardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_credit_card")
    @Column(name = "cod_credit_card")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cod_account")
    private AccountModel account;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "cvv")
    private Integer cvv;

    @Column(name = "credit_limit", scale = 2, precision = 13)
    private BigDecimal limit;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CreditCardTransactionModel> creditCardTransactions;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
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

    public List<CreditCardTransactionModel> getCreditCardTransactions() {
        return creditCardTransactions;
    }

    public void setCreditCardTransactions(List<CreditCardTransactionModel> creditCardTransactions) {
        this.creditCardTransactions = creditCardTransactions;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
}