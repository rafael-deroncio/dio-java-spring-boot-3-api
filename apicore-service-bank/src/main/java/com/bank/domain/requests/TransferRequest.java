package com.bank.domain.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class TransferRequest {

    @NotNull(message = "The 'Value' field cannot be null.")
    @PositiveOrZero(message = "The 'Value' must be a positive or zero value.")
    private BigDecimal Value;
    public BigDecimal getValue() {
        return Value;
    }

    public void setValue(BigDecimal value) {
        Value = value;
    }
}
