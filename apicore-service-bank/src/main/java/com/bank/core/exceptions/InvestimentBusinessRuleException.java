package com.bank.core.exceptions;

import com.bank.core.enums.ErrorResponseType;
import org.springframework.http.HttpStatus;

public class InvestimentBusinessRuleException extends BusinessRuleException {
    public InvestimentBusinessRuleException(String message) {
        super(message);
    }

    public InvestimentBusinessRuleException(String message, HttpStatus code, ErrorResponseType error) {
        super(message, code, error);
    }

    public InvestimentBusinessRuleException(String message, HttpStatus code) {
        super(message, code);
    }
}
