package com.bank.domain.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PixTransferRequest extends TransferRequest {

    @NotBlank(message = "The 'Key' field is required and must not be blank.")
    @Size(min = 5, max = 50, message = "The 'Key' must be between 5 and 50 characters.")
    private String Key;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
