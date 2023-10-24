package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.AccountBusinessRuleException;
import com.bank.core.exceptions.AgencyBusinessRuleException;
import com.bank.core.models.*;
import com.bank.core.repositories.BankRepository;
import com.bank.core.repositories.ClientRepository;
import com.bank.core.services.interfaces.IBankService;
import com.bank.core.utils.FormatterUtil;
import com.bank.domain.responses.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BankService implements IBankService {

    @Autowired
    BankRepository _bankRepository;

    @Autowired
    ClientRepository _clientRepository;

    @Autowired
    FormatterUtil _formatter;

    @Autowired
    ModelMapper _mapper;

    @Override
    public BankDetailsResponse getBankDetails() {
        BankModel bank = _bankRepository.getBank();
        if (bank == null) { return null; }

        BankDetailsResponse response = new BankDetailsResponse();
        response.setName(String.format("%s - %s", bank.getId(), bank.getName()));
        response.setCreatedDate(bank.getCreatedDate());

        List<AgencyResponse> agencies = new ArrayList<>();
        for (AgencyModel agency : bank.getAgencies()) {
            AgencyResponse agencyResponse = new AgencyResponse();

            String name = String.format("%s - %s", agency.getId(), agency.getAddress().getStreet());
            agencyResponse.setNumber(agency.getId());
            agencyResponse.setName(name);
            agencyResponse.setCreatedDate(agency.getCreatedDate());

            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setStreet(agency.getAddress().getStreet());
            addressResponse.setState(agency.getAddress().getState());
            addressResponse.setCity(agency.getAddress().getCity());
            addressResponse.setZipcode(agency.getAddress().getZipcode());

            agencyResponse.setAddress(addressResponse);

            agencies.add(agencyResponse);
        }

        response.setAgencies(agencies);

        BigDecimal capital = new BigDecimal(0);
        for (BigDecimal value : _bankRepository.getCapitalAccounts().values()) {
            capital = capital.add(value);
        }

        response.setCapital(capital);
        return response;
    }

    @Override
    public AgencyDetailsResponse getAgencyDetails(Integer number) {
        try {
            AgencyDetailsResponse response = null;

            List<AgencyResponse> bankDetails = getBankDetails().getAgencies();

            for (AgencyResponse agency : getBankDetails().getAgencies()) {
                if (agency.getNumber().equals(number)) {
                    response = _mapper.map(agency, AgencyDetailsResponse.class);
                    break;
                }
            }
            if (response.equals(null)) {
                throw new AgencyBusinessRuleException(String.format("agency with number '$s' not found", number),
                        HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
            }

            List<AccountDetailResponse> AccountDetails = new ArrayList<>();
            for (AccountModel account : _bankRepository.getAccountsByAgencyNumber(number)) {
                AccountDetailResponse accountDetailResponse = new AccountDetailResponse();

                accountDetailResponse.setNumber(account.getId());
                accountDetailResponse.setOpenDate(account.getCreatedDate());

                ClientModel client = _clientRepository.getClientById(account.getCodClient());
                String name = String.format("%s %s", client.getFirstName().toUpperCase(), client.getLastName().toUpperCase());
                accountDetailResponse.setOwner(name);
                accountDetailResponse.setOwnerId(account.getCodClient());

                BigDecimal capital = new BigDecimal(0);
                for (AccountTransactionModel transaction : account.getTransactions()) {
                    capital = capital.add(transaction.getAmount());
                }
                accountDetailResponse.setBalance(capital);
                AccountDetails.add(accountDetailResponse);
            }

            response.setAccounts(AccountDetails);
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public AccountResponse getAccountClient(ClientModel client) {
        AccountModel account = _bankRepository.getAccountClient(client);
        if (account == null) {
            throw new AccountBusinessRuleException(
                    String.format("customer does not have an open account"),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }

        BankModel bank = _bankRepository.getBank();

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAgency(account.getCodAgency());
        accountResponse.setNumber(account.getId());
        accountResponse.setBank(bank.getName());

        List<TransactionResponse> transactions = getTransactionResponses(accountResponse, account);
        accountResponse.setTransactions(transactions);

        CreditCardResponse creditCard = getCreditCardResponse(account);
        accountResponse.setCreditCard(creditCard);

        return accountResponse;
    }

    private CreditCardResponse getCreditCardResponse(AccountModel account) {
        CreditCardResponse creditCard = new CreditCardResponse();
        CreditCardModel creditCardModel = _bankRepository.getCreditCardAccount(account.getId());

        if (creditCardModel == null) {
            return creditCard;
        }

        List<TransactionResponse> trasactions = new ArrayList<>();
        for (CreditCardTransactionModel transactionModel: creditCardModel.getCreditCardTransactions()) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setDate(transactionModel.getCreatedDate());
            transactionResponse.setDescription(transactionModel.getDescription());
            transactionResponse.setEstablishment(transactionModel.getLocality());
            transactionResponse.setValue(transactionModel.getAmount());

            trasactions.add(transactionResponse);
        }

        creditCard.setNumber(_formatter.formatCreditCardNumber(creditCardModel.getCardNumber()));
        creditCard.setLimit(creditCardModel.getLimit());
        creditCard.setExpires(_formatter.formatExpyresDate(creditCardModel.getExpiryDate()));
        creditCard.setAvaliableLimit(creditCardModel.getLimit());
        creditCard.setTransactions(trasactions);

        BigDecimal avaliableLimit = calculateAvailableLimit(creditCardModel.getLimit(), trasactions);
        creditCard.setAvaliableLimit(avaliableLimit);

        return creditCard;
    }

    private BigDecimal calculateAvailableLimit(BigDecimal totalCreditLimit, List<TransactionResponse> transactions) {
        BigDecimal totalDebits = transactions.stream()
                .map(transaction -> transaction.getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCreditLimit.subtract(totalDebits);
    }

    private List<TransactionResponse> getTransactionResponses(AccountResponse accountResponse, AccountModel account) {
        accountResponse.setAmount(calculateTotalAmount(account));
        List<TransactionResponse> transactions = new ArrayList<>();
        for (AccountTransactionModel transactionModel: account.getTransactions()) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setDate(transactionModel.getCreatedDate());
            transactionResponse.setDescription(transactionModel.getDescription());
            transactionResponse.setEstablishment(transactionModel.getLocality());
            transactionResponse.setValue(transactionModel.getAmount());
            transactions.add(transactionResponse);
        }
        return transactions;
    }

    private BigDecimal calculateTotalAmount(AccountModel account) {
        List<AccountTransactionModel> transactions = account.getTransactions();

        if (transactions != null) {
            BigDecimal totalAmount = transactions.stream()
                    .map(AccountTransactionModel::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return totalAmount;
        }
        return BigDecimal.ZERO;
    }
}