package com.bank.core.configurations;

import com.bank.core.models.*;
import com.bank.core.repositories.BankRepository;
import com.bank.core.repositories.ClientRepository;
import com.bank.core.repositories.UserRepository;
import com.bank.core.utils.PasswordHasherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationConfiguration {

    private final Date TODAY = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    @Autowired
    BankRepository _bankRepository;
    @Autowired
    UserRepository _userRepository;
    @Autowired
    ClientRepository _clientRepository;
    @Autowired
    PasswordHasherUtil _passwordHasher;
    public void start() {
        startBank();
        startUser();
        startAccount();
        startCreditCard();
        startPix();
        startInvestiment();
    }

    private void startCreditCard() {
        AccountModel account = this._bankRepository.getAccount(999);

        CreditCardModel creditCard = new CreditCardModel();
        creditCard.setAccount(account);
        creditCard.setCardNumber("1234567890123456");
        creditCard.setExpiryDate(new Date());
        creditCard.setLimit(new BigDecimal("5000.00"));

        CreditCardTransactionModel transaction1 = new CreditCardTransactionModel();
        transaction1.setCreditCard(creditCard);
        transaction1.setTransactionDate(this.TODAY);
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setDescription("Compra em loja");
        transaction1.setLocality("Cidade A");

        CreditCardTransactionModel transaction2 = new CreditCardTransactionModel();
        transaction2.setCreditCard(creditCard);
        transaction2.setTransactionDate(this.TODAY);
        transaction2.setAmount(new BigDecimal("50.00"));
        transaction2.setDescription("Restaurante");
        transaction2.setLocality("Cidade B");

        List<CreditCardTransactionModel> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        creditCard.setCreditCardTransactions(transactionList);

        this._bankRepository.saveCreditCard(creditCard);
        this._bankRepository.saveCreditCardTransaction(transaction1);
        this._bankRepository.saveCreditCardTransaction(transaction2);
    }

    private void startInvestiment() {
        AccountModel account = this._bankRepository.getAccount(999);

        InvestmentModel investment = new InvestmentModel();
        investment.setAccount(account);
        investment.setNameInvestment("Meu Investimento");
        investment.setAmount(new BigDecimal("500.00"));
        investment.setStartDate(this.TODAY);

        InvestmentIncomesModel income1 = new InvestmentIncomesModel();
        income1.setInvestment(investment);
        income1.setIncomeDate(this.TODAY);
        income1.setAmount(new BigDecimal("250.00"));
        income1.setDescription("Rendimento mensal");

        InvestmentIncomesModel income2 = new InvestmentIncomesModel();
        income2.setInvestment(investment);
        income2.setIncomeDate(this.TODAY);
        income2.setAmount(new BigDecimal("250.00"));
        income2.setDescription("Rendimento mensal");

        List<InvestmentIncomesModel> incomeList = new ArrayList<>();
        incomeList.add(income1);
        incomeList.add(income2);
        investment.setInvestmentIncomes(incomeList);

        this._bankRepository.saveInvestment(investment);
        this._bankRepository.saveInvestmentIncomes(income1);
        this._bankRepository.saveInvestmentIncomes(income2);
    }

    private void startPix() {
        AccountModel account = this._bankRepository.getAccount(999);

        PixModel pix = new PixModel();
        pix.setAccount(account);

        PixDetailModel pixDetail = new PixDetailModel();
        pixDetail.setPixKey("rafael.deroncio@example");
        pixDetail.setPixKeyType("EMAIL");
        pixDetail.setDescription("testing method");
        pixDetail.setPix(pix);


        PixTransactionModel pixTransaction = new PixTransactionModel();
        pixTransaction.setPixKeyDestination("teste.teste@example.com");
        pixTransaction.setPixKeyTypeDestination("EMAIL");
        pixTransaction.setTransactionDate(this.TODAY);
        pixTransaction.setAmount(BigDecimal.valueOf(100.00));
        pixTransaction.setDescription("testing method");
        pixTransaction.setPix(pix);

        this._bankRepository.savePix(pix);
        this._bankRepository.savePixDetail(pixDetail);
        this._bankRepository.savePixTransaction(pixTransaction);
    }

    private void startAccount() {
        AccountModel account = new AccountModel();
        account.setCodClient(999);
        account.setCodAgency(999);
        account.setBalance(new BigDecimal("1000.00"));

        List<AccountTransactionModel> transactions = new ArrayList<>();
        AccountTransactionModel transaction = new AccountTransactionModel();
        transaction.setAccount(account);
        transaction.setTransactionDate(this.TODAY);
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setDescription("Depósito inicial");
        transaction.setLocality("Localidade 1");
        transactions.add(transaction);

        transaction.setAccount(account);
        account.setTransactions(transactions);

        this._bankRepository.saveAccount(account);
        this._bankRepository.saveAccountTransaction(transaction);
    }

    private void startUser() {
        UserModel user = new UserModel();
        user.setUsername("rafael.deroncio");
        user.setPasswordHash(_passwordHasher.encryptPassword("ffgaoi73wfn7xf"));

        ClientModel client = new ClientModel();
        client.setFirstName("Rafael");
        client.setLastName("Deroncio");
        client.setCpf("11122233300");
        client.setBirthDate(Date.from(LocalDate.of(1993, 10, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        client.setEmail("rafael.deroncio@example.com");
        client.setUser(user);

        ClientAddressModel address = new ClientAddressModel();
        address.setStreet("Avenida Francisco Leme");
        address.setComplement("23B");
        address.setNumber(52);
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipcode("04257-000");
        address.setClient(client);

        ClientTelephoneModel telephone = new ClientTelephoneModel();
        telephone.setPhoneNumber("11 9 9933-4455");
        telephone.setClient(client);

        _userRepository.saveUser(user);
        _clientRepository.saveClient(client);
        _clientRepository.saveClientAddress(address);
        _clientRepository.saveClientTelephone(telephone);
    }

    private void startBank() {
        BankModel bank = new BankModel();
        bank.setName("DevBank co.");

        AgencyModel agency = new AgencyModel();
        agency.setBank(bank);

        AgencyAddressModel address = new AgencyAddressModel();
        address.setStreet("Praça da Sé");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipcode("01001-000");
        address.setAgency(agency);


        this._bankRepository.saveBank(bank);
        this._bankRepository.saveAgency(agency);
        this._bankRepository.saveAgencyAddress(address);
    }
}