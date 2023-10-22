package com.bank.core.services;

import com.bank.core.services.interfaces.IBankOperationsService;
import com.bank.domain.requests.MoneyTransferRequest;
import com.bank.domain.requests.NewAccountRequest;
import com.bank.domain.requests.PixTransferRequest;
import com.bank.domain.responses.*;
import org.springframework.stereotype.Service;

@Service
public class BankOperationsService implements IBankOperationsService {
    @Override
    public NewAccountResponse openAccount(Integer agencyNumber, NewAccountRequest request) {
        return null;
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
