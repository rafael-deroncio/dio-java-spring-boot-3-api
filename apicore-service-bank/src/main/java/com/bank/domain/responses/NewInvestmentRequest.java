package com.bank.domain.responses;

import java.math.BigDecimal;

public class NewInvestmentRequest {
    private BigDecimal amount;
    private String nameInvestment;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNameInvestment() {
        return nameInvestment;
    }

    public void setNameInvestment(String nameInvestment) {
        this.nameInvestment = nameInvestment;
    }
}
