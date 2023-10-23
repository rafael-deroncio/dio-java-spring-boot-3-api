package com.bank.core.repositories.interfaces;

import com.bank.core.models.*;

public interface IBankRepository {
    BankModel saveBank(BankModel bank);
    BankModel getBank();
    AgencyModel saveAgency(AgencyModel agency);
    AgencyAddressModel saveAgencyAddress(AgencyAddressModel agencyAddress);


    AccountModel saveAccount(AccountModel account);
    AccountModel getAccount(Integer number);
    AccountTransactionModel saveAccountTransaction(AccountTransactionModel account);
    AccountModel getAccountClient(ClientModel client);


    CreditCardModel saveCreditCard(CreditCardModel creditCard);
    CreditCardModel getCreditCardAccount(Integer accountNumber);
    CreditCardTransactionModel saveCreditCardTransaction(CreditCardTransactionModel creditCardTransaction);


    PixModel savePix(PixModel pix);
    PixDetailModel savePixDetail(PixDetailModel PixDetail);
    PixTransactionModel savePixTransaction(PixTransactionModel pixTransaction);


    InvestmentModel saveInvestment(InvestmentModel investment);
    InvestmentIncomesModel saveInvestmentIncomes(InvestmentIncomesModel investmentIncomes);
}
