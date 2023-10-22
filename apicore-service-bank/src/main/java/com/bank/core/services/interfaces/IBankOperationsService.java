package com.bank.core.services.interfaces;

import com.bank.domain.requests.MoneyTransferRequest;
import com.bank.domain.requests.NewAccountRequest;
import com.bank.domain.requests.PixTransferRequest;
import com.bank.domain.responses.*;

public interface IBankOperationsService {
    NewAccountResponse openAccount(Integer agencyNumber, NewAccountRequest request);
    MoneyTransferResponse moneyTransfer(Integer accountNumber,MoneyTransferRequest request);
    PixTransferResponse pixTransfer(Integer accountNumber, PixTransferRequest request);
    NewInvestmentResponse invest(Integer accountNumber, NewInvestmentRequest request);
}
