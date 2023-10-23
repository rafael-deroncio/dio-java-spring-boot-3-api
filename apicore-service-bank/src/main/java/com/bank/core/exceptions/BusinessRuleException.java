package com.bank.core.exceptions;

import com.bank.core.enums.ErrorResponseType;
import org.springframework.http.HttpStatus;

public class BusinessRuleException extends RuntimeException {

    private Integer code;
    private String status;
    private String error;

    public BusinessRuleException(String message){
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        this.error = ErrorResponseType.Critical.toString();
    }

    public BusinessRuleException(String message, HttpStatus code, ErrorResponseType error){
        super(message);
        this.code = code.value();
        this.status = code.toString();
        this.error = error.toString();
    }

    public BusinessRuleException(String message, HttpStatus code){
        super(message);
        this.error = ErrorResponseType.Critical.toString();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
