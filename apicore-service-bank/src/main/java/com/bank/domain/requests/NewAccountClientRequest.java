package com.bank.domain.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class NewAccountClientRequest {
    @NotNull(message = "The 'cpf' field cannot be null.")
    private String username;

    @NotNull(message = "The 'income' field cannot be null.")
    @Positive(message = "The 'income' field must be a positive number.")
    private BigDecimal income;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
