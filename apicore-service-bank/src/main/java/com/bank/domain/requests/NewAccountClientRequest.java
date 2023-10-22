package com.bank.domain.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class NewAccountClientRequest {
    @NotNull(message = "The 'Id' field cannot be null.")
    private Integer Id;

    @NotNull(message = "The 'income' field cannot be null.")
    @Positive(message = "The 'income' field must be a positive number.")
    private BigDecimal income;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
