package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.AccountBusinessRuleException;
import com.bank.core.models.*;
import com.bank.core.repositories.BankRepository;
import com.bank.core.services.interfaces.IBankService;
import com.bank.core.utils.FormatterUtil;
import com.bank.domain.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankService implements IBankService {

    @Autowired
    BankRepository _bankRepository;

    @Autowired
    FormatterUtil _formatter;

    @Override
    public BankDetailsResponse getBankDetails() {
        return null;
    }

    @Override
    public AgencyDetailsResponse getAgencyDetails(Integer number) {
        return null;
    }

    @Override
    public AccountResponse gerAccountClient(ClientModel client) {
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

        List<TransactionResponse> trasactions = new ArrayList<>();
        for (AccountTransactionModel transactionModel: account.getTransactions()) {
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
        creditCard.setNumber(_formatter.formatCreditCardNumber(creditCardModel.getCardNumber()));

        BigDecimal avaliableLimit = calculateAvailableLimit(creditCardModel.getLimit(), trasactions);
        creditCard.setAvaliableLimit(avaliableLimit);
        creditCard.setTransactions(trasactions);
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
