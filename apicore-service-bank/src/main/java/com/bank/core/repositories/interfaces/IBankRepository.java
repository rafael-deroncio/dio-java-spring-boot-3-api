package com.bank.core.repositories.interfaces;

import com.bank.core.models.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This interface defines the methods for interacting with bank-related data.
 */
public interface IBankRepository {

    /**
     * Saves a bank record in the repository.
     *
     * @param bank The bank model to be saved.
     * @return The saved bank model.
     */
    BankModel saveBank(BankModel bank);

    /**
     * Retrieves a bank from the repository.
     *
     * @return The bank model.
     */
    BankModel getBank();

    /**
     * Saves an agency record in the repository.
     *
     * @param agency The agency model to be saved.
     * @return The saved agency model.
     */
    AgencyModel saveAgency(AgencyModel agency);

    AgencyModel getAgency(Integer number);

    /**
     * Saves an agency address in the repository.
     *
     * @param agencyAddress The agency address model to be saved.
     * @return The saved agency address model.
     */
    AgencyAddressModel saveAgencyAddress(AgencyAddressModel agencyAddress);

    /**
     * Retrieves capital accounts by their unique IDs.
     *
     * @return A map containing the capital accounts' IDs and their corresponding balances.
     */
    Map<Integer, BigDecimal> getCapitalAccounts();

    /**
     * Saves an account record in the repository.
     *
     * @param account The account model to be saved.
     * @return The saved account model.
     */
    AccountModel saveAccount(AccountModel account);

    /**
     * Retrieves an account by its unique number.
     *
     * @param number The account number.
     * @return The account model associated with the given number.
     */
    AccountModel getAccount(Integer number);

    /**
     * Saves an account transaction in the repository.
     *
     * @param accountTransaction The account transaction model to be saved.
     * @return The saved account transaction model.
     */
    AccountTransactionModel saveAccountTransaction(AccountTransactionModel accountTransaction);

    /**
     * Retrieves the account associated with a specific client.
     *
     * @param client The client model.
     * @return The account model associated with the given client.
     */
    AccountModel getAccountClient(ClientModel client);

    AccountModel getAccount(Integer agencyNumber, Integer accountNumber);

    /**
     * Retrieves a list of accounts by agency number.
     *
     * @param number The agency number.
     * @return A list of account models associated with the given agency number.
     */
    List<AccountModel> getAccountsByAgencyNumber(Integer number);

    /**
     * Saves a credit card record in the repository.
     *
     * @param creditCard The credit card model to be saved.
     * @return The saved credit card model.
     */
    CreditCardModel saveCreditCard(CreditCardModel creditCard);

    /**
     * Retrieves the credit card associated with a specific account number.
     *
     * @param accountNumber The account number.
     * @return The credit card model associated with the given account number.
     */
    CreditCardModel getCreditCardAccount(Integer accountNumber);

    /**
     * Saves a credit card transaction in the repository.
     *
     * @param creditCardTransaction The credit card transaction model to be saved.
     * @return The saved credit card transaction model.
     */
    CreditCardTransactionModel saveCreditCardTransaction(CreditCardTransactionModel creditCardTransaction);

    /**
     * Saves a PIX (instant payment) record in the repository.
     *
     * @param pix The PIX model to be saved.
     * @return The saved PIX model.
     */
    PixModel savePix(PixModel pix);

    PixModel getPixDetails(AccountModel account);

    PixModel getPixDetails(String key);

    /**
     * Saves PIX details in the repository.
     *
     * @param pixDetail The PIX detail model to be saved.
     * @return The saved PIX detail model.
     */
    PixDetailModel savePixDetail(PixDetailModel pixDetail);

    /**
     * Saves a PIX transaction in the repository.
     *
     * @param pixTransaction The PIX transaction model to be saved.
     * @return The saved PIX transaction model.
     */
    PixTransactionModel savePixTransaction(PixTransactionModel pixTransaction);

    /**
     * Saves an investment record in the repository.
     *
     * @param investment The investment model to be saved.
     * @return The saved investment model.
     */
    InvestmentModel saveInvestment(InvestmentModel investment);

    /**
     * Saves investment income details in the repository.
     *
     * @param investmentIncomes The investment income details model to be saved.
     * @return The saved investment income details model.
     */
    InvestmentIncomesModel saveInvestmentIncomes(InvestmentIncomesModel investmentIncomes);
}

