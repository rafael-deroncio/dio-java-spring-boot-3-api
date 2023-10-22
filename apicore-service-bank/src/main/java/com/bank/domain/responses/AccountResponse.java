package com.bank.domain.responses;

import java.math.BigDecimal;
import java.util.List;

public class AccountResponse {
    private String bank;
    private Integer agency;
    private Integer number;
    private BigDecimal amount;
    private List<TransactionResponse> transactions;
    private CreditCardResponse creditCard;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Integer getAgency() {
        return agency;
    }

    public void setAgency(Integer agency) {
        this.agency = agency;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }

    public CreditCardResponse getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardResponse creditCard) {
        this.creditCard = creditCard;
    }
}
