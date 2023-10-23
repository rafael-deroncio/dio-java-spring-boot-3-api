package com.bank.core.repositories;

import com.bank.core.models.*;
import com.bank.core.repositories.contexts.*;
import com.bank.core.repositories.interfaces.IBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BankRepository implements IBankRepository {

    @Autowired
    BankContextRepository _bankContextRepository;
    @Autowired
    AgencyContextRepository _agencyContextRepository;
    @Autowired
    AgencyAddressContextRepository _agencyAddressContextRepository;

    @Autowired
    AccountContextRepository _accountContextRepository;
    @Autowired
    AccountTransactionContextRepository _accountTransactionContextRepository;

    @Autowired
    PixContextRepository _pixContextRepository;
    @Autowired
    PixDetailContextRepository _pixDetailContextRepository;
    @Autowired
    PixTransactionContextRepository _pixTransactionContextRepository;

    @Autowired
    InvestmentContextRepository _investmentContextRepository;
    @Autowired
    InvestmentIncomesContextRepository _investmentIncomesContextRepository;

    @Autowired
    CreditCardContextRepository _creditCardContextRepository;
    @Autowired
    CreditCardTransactionContextRepository _creditCardTransactionContextRepository;


    @Override
    public BankModel saveBank(BankModel bank) {
        return this._bankContextRepository.save(bank);
    }

    @Override
    public AgencyModel saveAgency(AgencyModel agency) {
        return this._agencyContextRepository.save(agency);
    }

    @Override
    public AgencyAddressModel saveAgencyAddress(AgencyAddressModel agencyAddress) {
        return this._agencyAddressContextRepository.save(agencyAddress);
    }

    @Override
    public AccountModel saveAccount(AccountModel account) {
        return this._accountContextRepository.save(account);
    }

    @Override
    public AccountModel getAccount(Integer number) {
        return this._accountContextRepository.getReferenceById(number);
    }

    @Override
    public AccountTransactionModel saveAccountTransaction(AccountTransactionModel account) {
        return this._accountTransactionContextRepository.save(account);
    }

    @Override
    public CreditCardModel saveCreditCard(CreditCardModel creditCard) {
        return this._creditCardContextRepository.save(creditCard);
    }

    @Override
    public CreditCardTransactionModel saveCreditCardTransaction(CreditCardTransactionModel creditCardTransaction) {
        return this._creditCardTransactionContextRepository.save(creditCardTransaction);
    }

    @Override
    public PixModel savePix(PixModel pix) {
        return this._pixContextRepository.save(pix);
    }

    @Override
    public PixDetailModel savePixDetail(PixDetailModel PixDetail) {
        return this._pixDetailContextRepository.save(PixDetail);
    }

    @Override
    public PixTransactionModel savePixTransaction(PixTransactionModel pixTransaction) {
        return this._pixTransactionContextRepository.save(pixTransaction);
    }

    @Override
    public InvestmentModel saveInvestment(InvestmentModel investment) {
        return this._investmentContextRepository.save(investment);
    }

    @Override
    public InvestmentIncomesModel saveInvestmentIncomes(InvestmentIncomesModel investmentIncomes) {
        return this._investmentIncomesContextRepository.save(investmentIncomes);
    }


}
