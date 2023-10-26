package com.bank.core.exceptions;

import com.bank.core.enums.ErrorResponseType;
import org.springframework.http.HttpStatus;

public class PixBusinessRuleException extends BusinessRuleException {
    public PixBusinessRuleException(String message) {
        super(message);
    }

    public PixBusinessRuleException(String message, HttpStatus code, ErrorResponseType error) {
        super(message, code, error);
    }

    public PixBusinessRuleException(String message, HttpStatus code) {
        super(message, code);
    }
}
