package com.bank.domain.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class NewAccountRequest {
    @NotNull(message = "The 'score' field cannot be null.")
    @Positive(message = "The 'score' field must be a positive number.")
    @Max(value = 100, message = "The 'score' field cannot be greater than 100.")
    private Double score;

    @PositiveOrZero(message = "The 'initialDeposit' field must be a positive number or zero.")
    private BigDecimal initialDeposit;

    @NotNull(message = "The 'client' field cannot be null.")
    @Valid
    private NewAccountClientRequest client;

    @NotNull(message = "The 'registerPixKeys' field cannot be null.")
    private Boolean registerPixKeys;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public NewAccountClientRequest getClient() {
        return client;
    }

    public void setClient(NewAccountClientRequest client) {
        this.client = client;
    }

    public BigDecimal getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(BigDecimal initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public Boolean getRegisterPixKeys() {
        return registerPixKeys;
    }

    public void setRegisterPixKeys(Boolean registerPixKeys) {
        this.registerPixKeys = registerPixKeys;
    }
}
