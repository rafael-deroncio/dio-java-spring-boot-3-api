package com.bank.core.exceptions;

import com.bank.core.enums.ErrorResponseType;
import org.springframework.http.HttpStatus;

public class AgencyBusinessRuleException extends BusinessRuleException {
    public AgencyBusinessRuleException(String message){
        super(message);
    }

    public AgencyBusinessRuleException(String message, HttpStatus code, ErrorResponseType error) {
        super(message, code, error);
    }

    public AgencyBusinessRuleException(String message, HttpStatus code) {
        super(message, code);
    }
}
