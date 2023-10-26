package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.enums.PixKeyType;
import com.bank.core.enums.TransferOperationType;
import com.bank.core.exceptions.AccountBusinessRuleException;
import com.bank.core.exceptions.AgencyBusinessRuleException;
import com.bank.core.exceptions.PixBusinessRuleException;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankOperationsService implements IBankOperationsService {
    @Autowired
    FormatterUtil _formatter;
    @Autowired
    ClientService _clientService;
    @Autowired
    BankService _bankService;
    @Autowired
    UserService _userService;
    @Autowired
    BankRepository _bankRepository;
    @Autowired
    BankOperationsUtil _bankUtils;
    @Autowired
    ModelMapper _mapper;

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

        PixModel pix = null;
        if (request.getRegisterPixKeys() != null && request.getRegisterPixKeys()) {
            pix = savePix(user, account);
        }

        return getNewAccountResponse(request, bank, account, creditCard, pix);
    }

    private PixModel savePix(UserModel user, AccountModel account) {
        PixModel pix = new PixModel();

        List<PixDetailModel> pixDetails = new ArrayList<>();
        PixDetailModel cpfKey = new PixDetailModel();
        cpfKey.setPix(pix);
        cpfKey.setPixKey(user.getClient().getCpf());
        cpfKey.setPixKeyType(PixKeyType.CPF.toString());
        pixDetails.add(cpfKey);

        PixDetailModel emailKey = new PixDetailModel();
        emailKey.setPix(pix);
        emailKey.setPixKey(user.getClient().getEmail());
        emailKey.setPixKeyType(PixKeyType.EMAIL.toString());
        pixDetails.add(emailKey);

        PixDetailModel telephoneKey = new PixDetailModel();
        telephoneKey.setPix(pix);
        String telephone = _clientService.getClient(user.getClient().getId()).getTelephones().get(0).getPhoneNumber();
        telephoneKey.setPixKey(telephone);
        telephoneKey.setPixKeyType(PixKeyType.TELEPHONE.toString());
        pixDetails.add(telephoneKey);

        pix.setPixDetails(pixDetails);
        pix.setCodAccount(account.getId());
        pix.setCodBank(account.getCodBank());
        pix.setCodAgency(account.getCodAgency());

        this._bankRepository.savePix(pix);

        return pix;
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
                    String.format("origin account not found with 'account number: %s' and 'agency number: %s'",
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
        return _mapper.map(
                transfer(origin, destiny, request.getValue(), TransferOperationType.TRANSFER),
                MoneyTransferResponse.class);
    }

    @Override
    public PixTransferResponse pixTransfer(Integer agencyNumber, Integer accountNumber, PixTransferRequest request) {
        if (request.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new PixBusinessRuleException("the value cannot be negative",
                    HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        // TODO: Account Source
        AccountModel origin = _bankRepository.getAccount(agencyNumber, accountNumber);
        if (origin == null) {
            throw new PixBusinessRuleException(
                    String.format("origin account not found with 'account number: %s' and 'agency number: %s'",
                            accountNumber, agencyNumber), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        PixModel pixOrigin = _bankRepository.getPixDetails(origin);
        if (pixOrigin.getPixDetails().isEmpty()) {
            throw new PixBusinessRuleException(
                    String.format("origin account 'account number: %s' and 'agency number: %s' does not contain pix key data",
                            accountNumber, agencyNumber), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        PixModel pixDestiny = _bankRepository.getPixDetails(request.getKey().trim().toLowerCase());
        if (pixDestiny == null)
            if (pixOrigin.getPixDetails().isEmpty()) {
                throw new PixBusinessRuleException(
                        String.format("pix key '%s' not found",
                                accountNumber, agencyNumber), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
            }
        AccountModel destiny = _bankRepository.getAccount(pixDestiny.getCodAgency(), pixDestiny.getCodAccount());

        // TODO: Transfer
        return _mapper.map(
                transfer(origin, destiny, request.getValue(), TransferOperationType.PIX),
                PixTransferResponse.class);
    }

    @Override
    public NewInvestmentResponse invest(Integer accountNumber, NewInvestmentRequest request) {
        return null;
    }

    private TransferResponse transfer(AccountModel origin, AccountModel destiny, BigDecimal value, TransferOperationType transferOperationType) {

        if (origin.getBalance().compareTo(value) < 0) {
            throw new AccountBusinessRuleException("insufficient balance for the transfer",
            HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        ClientModel clientOrigin = _clientService .getClient(origin.getCodClient());
        ClientModel clientDestiny = _clientService.getClient(destiny.getCodClient());

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

        return getTransferResponse(origin, value, clientDestiny, previousAmount, transferOperationType);

    }

    private NewAccountResponse getNewAccountResponse(NewAccountRequest request, BankModel bank, AccountModel account, CreditCardModel creditCard, PixModel pix) {
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

        if (pix != null) {
            response.setPix(_bankService.getPixResponses(pix));
        }

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

    private TransferResponse getTransferResponse(AccountModel origin, BigDecimal value, ClientModel client, BigDecimal newBalance, TransferOperationType transferOperationType) {
        TransferResponse response = new TransferResponse();
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
