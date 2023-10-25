package com.bank.core.repositories;

import com.bank.core.models.*;
import com.bank.core.repositories.contexts.*;
import com.bank.core.repositories.interfaces.IBankRepository;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public BankModel getBank() {
        return this._bankContextRepository.getBank();
    }

    @Override
    public Map<Integer, BigDecimal> getCapitalAccounts() {
        Map<Integer, BigDecimal> capitalAccounts = new HashMap<>();
        List<AccountModel> accounts = _accountContextRepository.getAccounts();

        for (AccountModel account : accounts) {
            BigDecimal totalAmount = BigDecimal.ZERO;

            if (account.getTransactions() != null) {
                for (AccountTransactionModel transaction : account.getTransactions()) {
                    totalAmount = totalAmount.add(transaction.getAmount());
                }
            }

            capitalAccounts.put(account.getCodAgency(), totalAmount);
        }

        return capitalAccounts;
    }

    @Override
    public   List<AccountModel> getAccountsByAgencyNumber(Integer number){
        return this._accountContextRepository.getAccountsByAgencyNumber(number);
    }

    @Override
    public AgencyModel saveAgency(AgencyModel agency) {
        return this._agencyContextRepository.save(agency);
    }

    @Override
    public AgencyModel getAgency(Integer number) {
        return this._agencyContextRepository.findById(number).orElse(null);
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
    public AccountModel getAccountClient(ClientModel client) {
        return this._accountContextRepository.getAccountByClientId(client.getId());
    }

    @Override
    public AccountTransactionModel saveAccountTransaction(AccountTransactionModel account) {
        return this._accountTransactionContextRepository.save(account);
    }

    @Override
    public AccountModel getAccount(Integer agencyNumber, Integer accountNumber) {
        return this._accountContextRepository.getAccount(agencyNumber, accountNumber);
    }

    @Override
    public CreditCardModel saveCreditCard(CreditCardModel creditCard) {
        return this._creditCardContextRepository.save(creditCard);
    }

    @Override
    public CreditCardModel getCreditCardAccount(Integer accountNumber) {
        return _creditCardContextRepository.findAll()
                .stream()
                .filter(credit -> credit.getAccount().getId() == accountNumber.intValue())
                .findFirst().orElse(null);
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
