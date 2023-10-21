package com.bank.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_pix_transaction")
@SequenceGenerator(name = "seq_cod_pix_transaction", sequenceName = "seq_cod_pix_transaction", initialValue = 999, allocationSize = 1)
public class PixTransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_pix_transaction")
    @Column(name = "cod_pix_transaction")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_pix")
    private PixModel pix;

    @Column(name = "pix_key_destination")
    private String pixKeyDestination;

    @Column(name = "pix_key_type_destination")
    private String pixKeyTypeDestination;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "amount", scale = 13, precision = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

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

    public PixModel getPix() {
        return pix;
    }

    public void setPix(PixModel pix) {
        this.pix = pix;
    }

    public String getPixKeyDestination() {
        return pixKeyDestination;
    }

    public void setPixKeyDestination(String pixKeyDestination) {
        this.pixKeyDestination = pixKeyDestination;
    }

    public String getPixKeyTypeDestination() {
        return pixKeyTypeDestination;
    }

    public void setPixKeyTypeDestination(String pixKeyTypeDestination) {
        this.pixKeyTypeDestination = pixKeyTypeDestination;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
