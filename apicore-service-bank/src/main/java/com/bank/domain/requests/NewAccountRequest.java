package com.bank.domain.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class NewAccountRequest {
    @NotNull(message = "The 'score' field cannot be null.")
    @Positive(message = "The 'score' field must be a positive number.")
    private Double score;

    @NotNull(message = "The 'client' field cannot be null.")
    @Valid
    private NewAccountClientRequest client;

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
}
