package com.bank.core.services.interfaces;

import com.bank.core.models.ClientModel;
import com.bank.domain.responses.AccountResponse;
import com.bank.domain.responses.AgencyDetailsResponse;
import com.bank.domain.responses.AgencyResponse;
import com.bank.domain.responses.BankDetailsResponse;

public interface IBankService {
    BankDetailsResponse getBankDetails();
    AgencyDetailsResponse getAgencyDetails(Integer number);
        AccountResponse gerAccountClient(ClientModel client);
}
