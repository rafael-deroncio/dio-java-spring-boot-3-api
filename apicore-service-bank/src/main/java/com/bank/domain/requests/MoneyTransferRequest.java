package com.bank.domain.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MoneyTransferRequest extends TransferRequest{
    @NotNull(message = "The 'accountNumber' field cannot be null.")
    @Min(value = 1, message = "The 'agencyNumber' must be a positive integer.")
    private Integer agencyNumber;

    @NotNull(message = "The 'accountNumber' field cannot be null.")
    @Min(value = 1, message = "The 'accountNumber' must be a positive integer.")
    private Integer accountNumber;

    public Integer getAgencyNumber() {
        return agencyNumber;
    }

    public void setAgencyNumber(Integer agencyNumber) {
        this.agencyNumber = agencyNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }
}
