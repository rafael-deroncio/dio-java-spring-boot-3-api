package com.bank.domain.responses;

import java.math.BigDecimal;

public class TransferResponse {
    private BigDecimal previousAmount;
    private String targetAccount;
    private BigDecimal transferValue;
    private BigDecimal updatedAmount;

    public BigDecimal getPreviousAmount() {
        return previousAmount;
    }
    public void setPreviousAmount(BigDecimal previousAmount) {
        this.previousAmount = previousAmount;
    }
    public String getTargetAccount() {
        return targetAccount;
    }
    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }
    public BigDecimal getTransferValue() {
        return transferValue;
    }
    public void setTransferValue(BigDecimal transferValue) {
        this.transferValue = transferValue;
    }
    public BigDecimal getUpdatedAmount() {
        return updatedAmount;
    }
    public void setUpdatedAmount(BigDecimal updatedAmount) {
        this.updatedAmount = updatedAmount;
    }

}
