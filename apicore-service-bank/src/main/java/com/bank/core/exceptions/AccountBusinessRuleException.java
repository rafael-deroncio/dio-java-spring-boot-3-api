package com.bank.core.exceptions;

import com.bank.core.enums.ErrorResponseType;
import org.springframework.http.HttpStatus;

public class AccountBusinessRuleException extends BusinessRuleException {
    public AccountBusinessRuleException(String message) {
        super(message);
    }

    public AccountBusinessRuleException(String message, HttpStatus code, ErrorResponseType error) {
        super(message, code, error);
    }

    public AccountBusinessRuleException(String message, HttpStatus code) {
        super(message, code);
    }
}
