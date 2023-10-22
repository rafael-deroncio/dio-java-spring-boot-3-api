package com.bank.domain.responses;

import java.math.BigDecimal;

public class NewInvestmentResponse {
    private BigDecimal Value;
    private String nameInvestment;
    private Double monthlyFee;

    public BigDecimal getValue() {
        return Value;
    }

    public void setValue(BigDecimal value) {
        Value = value;
    }

    public String getNameInvestment() {
        return nameInvestment;
    }

    public void setNameInvestment(String nameInvestment) {
        this.nameInvestment = nameInvestment;
    }

    public Double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(Double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}
