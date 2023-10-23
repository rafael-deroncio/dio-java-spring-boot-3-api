package com.bank.core.exceptions;

import com.bank.core.enums.ErrorResponseType;
import org.springframework.http.HttpStatus;

public class ClientBusinessRuleException extends BusinessRuleException {
    public ClientBusinessRuleException(String message){
        super(message);
    }

    public ClientBusinessRuleException(String message, HttpStatus code, ErrorResponseType error) {
        super(message, code, error);
    }

    public ClientBusinessRuleException(String message, HttpStatus code) {
        super(message, code);
    }
}
