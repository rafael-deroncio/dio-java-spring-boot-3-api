package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.enums.TransferOperationType;
import com.bank.core.exceptions.AccountBusinessRuleException;
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
import org.apache.catalina.User;
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
    @Autowired
    FormatterUtil _formatter;
    @Autowired
    ClientService _clientService;

    @Autowired
    UserService _userService;

    @Autowired
    BankRepository _bankRepository;
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

    @Override
    public MoneyTransferResponse moneyTransfer(Integer agencyNumber, Integer accountNumber, MoneyTransferRequest request) {
        if (request.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountBusinessRuleException("the value cannot be negative",
                    HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        // TODO: Account Source
        AccountModel origin = _bankRepository.getAccount(agencyNumber, accountNumber);
        if (origin == null) {
            throw new AccountBusinessRuleException(
                    String.format("account origin not found with 'account number: %s' and 'agency number: %s'",
                            accountNumber, agencyNumber), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        // TODO: Account Destiny
        AccountModel destiny = _bankRepository.getAccount(request.getAgencyNumber(), request.getAccountNumber());
        if (destiny == null) {
            throw new AccountBusinessRuleException(
                    String.format("account destiny not found with 'account number: %s' and 'agency number: %s'",
                            request.getAgencyNumber(), request.getAccountNumber()), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        // TODO: Transfer
        return transfer(origin, destiny, request.getValue(), TransferOperationType.TRANSFER);
    }

    @Override
    public PixTransferResponse pixTransfer(Integer accountNumber, PixTransferRequest request) {
        return null;
    }

    @Override
    public NewInvestmentResponse invest(Integer accountNumber, NewInvestmentRequest request) {
        return null;
    }

    private MoneyTransferResponse transfer(AccountModel origin, AccountModel destiny, BigDecimal value, TransferOperationType transferOperationType) {

        if (origin.getBalance().compareTo(value) < 0) {
            throw new AccountBusinessRuleException("insufficient balance for the transfer",
            HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        ClientModel clientOrigin = _clientService .getClient(origin.getCodClient());
        ClientModel clientDestiny = _clientService.getClient(destiny.getCodClient());

        switch (transferOperationType) {
            case TRANSFER:
                // TODO: Process origin
                AccountTransactionModel accountTransaction = new AccountTransactionModel();
                accountTransaction.setAccount(origin);
                accountTransaction.setAmount(value.negate());
                accountTransaction.setDescription(getDescptionFormated(destiny, transferOperationType));
                accountTransaction.setLocality(getLocalityFormated(transferOperationType, clientDestiny));
                accountTransaction.setTransactionDate(this._formatter.getTodayDate());

                List<AccountTransactionModel> accountTransactions = origin.getTransactions();
                accountTransactions.add(accountTransaction);
                origin.setTransactions(accountTransactions);

                BigDecimal previousAmount = origin.getBalance();
                origin.setBalance(previousAmount.subtract(value));

                // TODO: Process destiny
                accountTransaction = new AccountTransactionModel();
                accountTransaction.setAccount(destiny);
                accountTransaction.setAmount(value);
                accountTransaction.setDescription(getDescptionFormated(origin, transferOperationType));
                accountTransaction.setLocality(getLocalityFormated(transferOperationType, clientOrigin));
                accountTransaction.setTransactionDate(this._formatter.getTodayDate());

                accountTransactions = destiny.getTransactions();
                accountTransactions.add(accountTransaction);
                destiny.setTransactions(accountTransactions);
                destiny.setBalance(destiny.getBalance().add(value));

                // TODO: Save
                this._bankRepository.saveAccount(origin);
                this._bankRepository.saveAccount(destiny);

                return getMoneyTransferResponse(origin, value, clientDestiny, previousAmount, TransferOperationType.TRANSFER);

            case PIX:
                return getMoneyTransferResponse(origin, value, clientDestiny, null, TransferOperationType.PIX);

            default:
                throw new AccountBusinessRuleException("invalid operation",
                        HttpStatus.UNPROCESSABLE_ENTITY, ErrorResponseType.Critical);
        }
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
            transaction.setTransactionDate(this._formatter.getTodayDate());
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

    private MoneyTransferResponse getMoneyTransferResponse(AccountModel origin, BigDecimal value, ClientModel client, BigDecimal newBalance, TransferOperationType transferOperationType) {
        MoneyTransferResponse response = new MoneyTransferResponse();
        response.setPreviousAmount(origin.getBalance());
        response.setTransferValue(value);
        response.setTargetAccount(getLocalityFormated(transferOperationType, client));
        response.setUpdatedAmount(newBalance);
        return response;
    }

    private static String getLocalityFormated(TransferOperationType transferOperationType, ClientModel clinet) {
        return String.format("%s: %s", transferOperationType.toString(),
                String.format("%s %s CPF %s", clinet.getFirstName().toUpperCase(), clinet.getLastName().toUpperCase(), clinet.getCpf()));
    }

    private static String getDescptionFormated(AccountModel account, TransferOperationType transferOperationType) {
        return String.format("operation: %s bank: %s agency: %s account: %s",
                transferOperationType.TRANSFER.toString(), account.getCodBank(), account.getCodAgency(), account.getId());
    }
}
