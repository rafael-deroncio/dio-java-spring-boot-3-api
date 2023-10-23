package com.bank.core.exceptions;

import com.bank.core.enums.ErrorResponseType;
import org.springframework.http.HttpStatus;

public class UserBusinessRuleException extends BusinessRuleException {
    public UserBusinessRuleException(String message) {
        super(message);
    }

    public UserBusinessRuleException(String message, HttpStatus code, ErrorResponseType error) {
        super(message, code, error);
    }

    public UserBusinessRuleException(String message, HttpStatus code) {
        super(message, code);
    }
}
