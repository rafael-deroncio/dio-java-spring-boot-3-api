package com.bank.core.services;

import com.bank.core.services.interfaces.IBankService;
import com.bank.domain.responses.AgencyDetailsResponse;
import com.bank.domain.responses.BankDetailsResponse;
import org.springframework.stereotype.Service;

@Service
public class BankService implements IBankService {
    @Override
    public BankDetailsResponse getBankDetails() {
        return null;
    }

    @Override
    public AgencyDetailsResponse getAgencyDetails(Integer number) {
        return null;
    }
}
