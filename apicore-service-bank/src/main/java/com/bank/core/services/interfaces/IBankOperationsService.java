package com.bank.core.services.interfaces;

import com.bank.core.models.PixModel;
import com.bank.domain.requests.MoneyTransferRequest;
import com.bank.domain.requests.NewAccountRequest;
import com.bank.domain.requests.PixTransferRequest;
import com.bank.domain.responses.*;

import java.util.List;

/**
 * This interface defines the service methods for performing bank operations.
 */
public interface IBankOperationsService {

    /**
     * Opens a new account for a client at the specified agency.
     *
     * @param agencyNumber The number of the agency where the account will be opened.
     * @param request      The request containing information for opening the account.
     * @return A response with details of the newly opened account.
     */
    NewAccountResponse openAccount(Integer agencyNumber, NewAccountRequest request);

    /**
     * Transfers money from one account to another.
     *
     * @param agencyNumber The agency number from which the money will be transferred.
     * @param accountNumber The account number from which the money will be transferred.
     * @param request       The request containing information about the money transfer.
     * @return A response with details of the money transfer operation.
     */
    MoneyTransferResponse moneyTransfer(Integer agencyNumber, Integer accountNumber, MoneyTransferRequest request);

    /**
     * Performs a PIX transfer from the specified account.
     *
     * @param accountNumber The account number from which the PIX transfer will be initiated.
     * @param request       The request containing information for the PIX transfer.
     * @return A response with details of the PIX transfer operation.
     */
    PixTransferResponse pixTransfer(Integer agencyNumber, Integer accountNumber, PixTransferRequest request);

    NewInvestmentResponse invest(Integer agencyNumber, Integer accountNumber, NewInvestmentRequest request);
}