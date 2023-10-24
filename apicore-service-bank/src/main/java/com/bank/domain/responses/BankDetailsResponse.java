package com.bank.domain.responses;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BankDetailsResponse {
    private String name;
    private Date createdDate;
    private BigDecimal capital;
    private List<AgencyResponse> agencies;

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public List<AgencyResponse> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<AgencyResponse> agencies) {
        this.agencies = agencies;
    }
}
