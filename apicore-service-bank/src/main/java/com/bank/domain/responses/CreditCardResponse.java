package com.bank.domain.responses;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CreditCardResponse {
    private String number;
    private String expires;
    private BigDecimal limit;
    private List<TransactionResponse> transactions;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}
