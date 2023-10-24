package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.enums.TransferOperationType;
import com.bank.core.exceptions.AgencyBusinessRuleException;
import com.bank.core.exceptions.UserBusinessRuleException;
import com.bank.core.models.*;
import com.bank.core.repositories.BankRepository;
import com.bank.core.services.interfaces.IBankOperationsService;
import com.bank.core.utils.BankOperationsUtil;
import com.bank.core.utils.FormatterUtil;
import com.bank.domain.requests.MoneyTransferRequest;
import com.bank.domain.requests.NewAccountRequest;
import com.bank.domain.requests.PixTransferRequest;
import com.bank.domain.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BankOperationsService implements IBankOperationsService {
    private final Date TODAY = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    @Autowired
    UserService _userService;
    @Autowired
    BankRepository _bankRepository;
    @Autowired
    FormatterUtil _formatter;

    @Autowired
    BankOperationsUtil _bankUtils;

    @Override
    public NewAccountResponse openAccount(Integer agencyNumber, NewAccountRequest request) {
        UserModel user = _userService.getUserModel(
                _formatter.formatUsername(request.getClient().getUsername()));

        if (user == null) {
            throw new UserBusinessRuleException(String.format("cliente with username 's%l not found", request.getClient().getUsername()),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }

        AgencyModel agency = _bankRepository.getAgency(agencyNumber);

        if (agency == null) {
            throw new AgencyBusinessRuleException(String.format("agengy s% not found", agencyNumber),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }

        BankModel bank = _bankRepository.getBank();

        AccountModel account = getAccountModel(request, user, agency, bank);
        this._bankRepository.saveAccount(account);

        CreditCardModel creditCard = getCreditCardModel(request, account);
        this._bankRepository.saveCreditCard(creditCard);

        return getNewAccountResponse(request, bank, account, creditCard);
    }

    private NewAccountResponse getNewAccountResponse(NewAccountRequest request, BankModel bank, AccountModel account, CreditCardModel creditCard) {
        NewAccountResponse response =  new NewAccountResponse();
        String bankLabel = String.format("%s - %s", bank.getId(), bank.getName());
        response.setBank(bankLabel);
        response.setAgency(account.getCodAgency());
        response.setNumber(account.getId());
        BigDecimal amount = request.getInitialDeposit() == null ? BigDecimal.ZERO : request.getInitialDeposit();
        response.setAmount(amount);

        List<TransactionResponse> transactions;

        if (account.getTransactions().isEmpty() || account.getTransactions() == null) {
            transactions = null;
        } else {
            transactions = new ArrayList<>();

            for (AccountTransactionModel transaction : account.getTransactions()) {
                TransactionResponse transactionResponse = new TransactionResponse();
                transactionResponse.setValue(transaction.getAmount());
                transactionResponse.setDate(transaction.getCreatedDate());
                transactionResponse.setDescription(transaction.getDescription());
                transactionResponse.setEstablishment(transaction.getLocality());

                transactions.add(transactionResponse);
            }
        }
        response.setTransactions(transactions);

        CreditCardResponse creditCardResponse = new CreditCardResponse();
        creditCardResponse.setNumber(creditCard.getCardNumber());
        creditCardResponse.setExpires(_bankUtils.getExpiryDateString(creditCard.getExpiryDate()));
        creditCardResponse.setAvaliableLimit(creditCard.getLimit());
        creditCardResponse.setLimit(creditCard.getLimit());
        creditCardResponse.setTransactions(null);

        response.setCreditCard(creditCardResponse);

        return response;
    }

    private CreditCardModel getCreditCardModel(NewAccountRequest request, AccountModel account) {
        CreditCardModel creditCard = new CreditCardModel();
        creditCard.setAccount(account);
        creditCard.setCardNumber(_bankUtils.generateCreditCardNumber());
        creditCard.setLimit(_bankUtils.calculateCreditLimit(request.getClient().getIncome(), request.getScore()));
        creditCard.setExpiryDate(_bankUtils.generateExpiryDate());
        creditCard.setCvv(Integer.getInteger(_bankUtils.generateCVV()));
        return creditCard;
    }

    private AccountModel getAccountModel(NewAccountRequest request, UserModel user, AgencyModel agency,BankModel bank) {
        AccountModel account = new AccountModel();
        account.setCodClient(user.getClient().getId());
        account.setCodBank(bank.getId());
        account.setCodAgency(agency.getId());

        if (request.getInitialDeposit().compareTo(BigDecimal.ZERO) > 0){
            List<AccountTransactionModel> transactions = new ArrayList<>();
            AccountTransactionModel transaction = new AccountTransactionModel();
            transaction.setAccount(account);
            transaction.setTransactionDate(this.TODAY);
            transaction.setAmount(request.getInitialDeposit());
            transaction.setDescription(TransferOperationType.INITIAL_DEPOSIT.toString());
            transaction.setLocality("open account");

            transactions.add(transaction);

            transaction.setAccount(account);
            account.setTransactions(transactions);

            account.setBalance(request.getInitialDeposit());
        }
        return account;
    }

    @Override
    public MoneyTransferResponse moneyTransfer(Integer accountNumber, MoneyTransferRequest request) {
        return null;
    }

    @Override
    public PixTransferResponse pixTransfer(Integer accountNumber, PixTransferRequest request) {
        return null;
    }

    @Override
    public NewInvestmentResponse invest(Integer accountNumber, NewInvestmentRequest request) {
        return null;
    }
}
