package com.bank.core.services.interfaces;

import com.bank.domain.requests.MoneyTransferRequest;
import com.bank.domain.requests.NewAccountRequest;
import com.bank.domain.requests.PixTransferRequest;
import com.bank.domain.responses.*;

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
     * @param accountNumber The account number from which the money will be transferred.
     * @param request       The request containing information about the money transfer.
     * @return A response with details of the money transfer operation.
     */
    MoneyTransferResponse moneyTransfer(Integer accountNumber, MoneyTransferRequest request);

    /**
     * Performs a PIX transfer from the specified account.
     *
     * @param accountNumber The account number from which the PIX transfer will be initiated.
     * @param request       The request containing information for the PIX transfer.
     * @return A response with details of the PIX transfer operation.
     */
    PixTransferResponse pixTransfer(Integer accountNumber, PixTransferRequest request);

    /**
     * Initiates a new investment for the specified account.
     *
     * @param accountNumber The account number for which the investment will be initiated.
     * @param request       The request containing information for the new investment.
     * @return A response with details of the newly initiated investment.
     */
    NewInvestmentResponse invest(Integer accountNumber, NewInvestmentRequest request);
}