package com.bank.domain.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class NewInvestmentResponse {
    private BigDecimal Value;
    private String nameInvestment;
    private BigDecimal monthlyFee;
    private List<Map<String, BigDecimal>> forecastsReturn;

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

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public List<Map<String, BigDecimal>> forecastsReturn() {
        return forecastsReturn;
    }

    public void setForecastReturn(List<Map<String, BigDecimal>> forecastsReturn) {
        this.forecastsReturn = forecastsReturn;
    }
}
