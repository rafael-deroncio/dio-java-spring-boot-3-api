package com.bank.core.repositories.interfaces;

import com.bank.core.models.*;

public interface IBankRepository {
    BankModel saveBank(BankModel bank);
    AgencyModel saveAgency(AgencyModel agency);
    AgencyAddressModel saveAgencyAddress(AgencyAddressModel agencyAddress);


    AccountModel saveAccount(AccountModel account);
    AccountModel getAccount(Integer number);
    AccountTransactionModel saveAccountTransaction(AccountTransactionModel account);


    CreditCardModel saveCreditCard(CreditCardModel creditCard);
    CreditCardTransactionModel saveCreditCardTransaction(CreditCardTransactionModel creditCardTransaction);


    PixModel savePix(PixModel pix);
    PixDetailModel savePixDetail(PixDetailModel PixDetail);
    PixTransactionModel savePixTransaction(PixTransactionModel pixTransaction);


    InvestmentModel saveInvestment(InvestmentModel investment);
    InvestmentIncomesModel saveInvestmentIncomes(InvestmentIncomesModel investmentIncomes);
}
