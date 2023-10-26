package com.bank.core.services.interfaces;

import com.bank.core.models.AccountModel;
import com.bank.core.models.ClientModel;
import com.bank.core.models.PixModel;
import com.bank.domain.responses.AccountResponse;
import com.bank.domain.responses.AgencyDetailsResponse;
import com.bank.domain.responses.BankDetailsResponse;
import com.bank.domain.responses.PixResponse;

import java.util.List;

/**
 * This interface defines the service methods for retrieving bank-related details.
 */
public interface IBankService {

    /**
     * Retrieves details about the bank.
     *
     * @return A response containing bank details.
     */
    BankDetailsResponse getBankDetails();

    /**
     * Retrieves details about an agency by its number.
     *
     * @param number The agency number.
     * @return A response containing agency details for the specified number.
     */
    AgencyDetailsResponse getAgencyDetails(Integer number);

    /**
     * Retrieves account information for a specific client.
     *
     * @param client The client model for whom account information is requested.
     * @return A response containing account details for the client.
     */
    AccountResponse getAccountClient(ClientModel client);

    List<PixResponse> getPixResponses(PixModel pix);
}
