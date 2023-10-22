package com.bank.domain.responses;

import java.util.Date;
import java.util.List;

public class AgencyDetailsResponse extends AgencyResponse {
    private List<AccountDetailResponse> accounts;

    public List<AccountDetailResponse> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDetailResponse> accounts) {
        this.accounts = accounts;
    }
}
