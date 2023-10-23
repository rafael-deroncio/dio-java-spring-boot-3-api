package com.bank.core.handlers;


import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.*;
import com.bank.domain.responses.ExceptionResponse;
import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.
import java.lang.reflect.UndeclaredThrowableException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Resource
    private MessageSource messageSource;

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ExceptionResponse exceptionResponse(String message, Integer code, String status, String error) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(message);
        exceptionResponse.setCode(code);
        exceptionResponse.setStatus(status);
        exceptionResponse.setError(error);
        return exceptionResponse;
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {

        if (e instanceof UndeclaredThrowableException) {
            UndeclaredThrowableException exception = (UndeclaredThrowableException) e;
            Throwable undeclaredThrowable = exception.getUndeclaredThrowable();

            if (undeclaredThrowable instanceof UserBusinessRuleException) {
                return handleApiException((UserBusinessRuleException) undeclaredThrowable, request);
            } else if (undeclaredThrowable instanceof ClientBusinessRuleException) {
                return handleApiException((ClientBusinessRuleException) undeclaredThrowable, request);
            } else if (undeclaredThrowable instanceof AccountBusinessRuleException) {
                return handleApiException((AccountBusinessRuleException) undeclaredThrowable, request);
            } else if (undeclaredThrowable instanceof AgencyBusinessRuleException) {
                return handleApiException((AgencyBusinessRuleException) undeclaredThrowable, request);
            } else if (undeclaredThrowable instanceof CreditCardBusinessRuleException) {
                return handleApiException((CreditCardBusinessRuleException) undeclaredThrowable, request);
            } else if (undeclaredThrowable instanceof PixBusinessRuleException) {
                return handleApiException((PixBusinessRuleException) undeclaredThrowable, request);
            } else if (undeclaredThrowable instanceof InvestimentBusinessRuleException) {
                return handleApiException((InvestimentBusinessRuleException) undeclaredThrowable, request);
            }
        }

        String message = messageSource.getMessage("error.server", new Object[]{e.getMessage()}, null);
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseType type = ErrorResponseType.Critical;

        ExceptionResponse error = exceptionResponse(message, code.value(), code.toString(), type.toString());

        return handleExceptionInternal(e, error, headers(), code, request);
    }


    @ExceptionHandler({UserBusinessRuleException.class})
    private ResponseEntity<Object> handleApiException(UserBusinessRuleException exception, WebRequest request) {

        ExceptionResponse response = exceptionResponse(
                exception.getMessage().toString(),
                exception.getCode(),
                exception.getStatus(),
                exception.getError());

        return handleExceptionInternal(exception, response, headers(), HttpStatus.valueOf(exception.getCode()), request);
    }

    @ExceptionHandler({ClientBusinessRuleException.class})
    private ResponseEntity<Object> handleApiException(ClientBusinessRuleException exception, WebRequest request) {

        ExceptionResponse response = exceptionResponse(
                exception.getMessage().toString(),
                exception.getCode(),
                exception.getStatus(),
                exception.getError());

        return handleExceptionInternal(exception, response, headers(), HttpStatus.valueOf(exception.getCode()), request);
    }

    @ExceptionHandler({AccountBusinessRuleException.class})
    private ResponseEntity<Object> handleApiException(AccountBusinessRuleException exception, WebRequest request) {

        ExceptionResponse response = exceptionResponse(
                exception.getMessage().toString(),
                exception.getCode(),
                exception.getStatus(),
                exception.getError());

        return handleExceptionInternal(exception, response, headers(), HttpStatus.valueOf(exception.getCode()), request);
    }

    @ExceptionHandler({AgencyBusinessRuleException.class})
    private ResponseEntity<Object> handleApiException(AgencyBusinessRuleException exception, WebRequest request) {

        ExceptionResponse response = exceptionResponse(
                exception.getMessage().toString(),
                exception.getCode(),
                exception.getStatus(),
                exception.getError());

        return handleExceptionInternal(exception, response, headers(), HttpStatus.valueOf(exception.getCode()), request);
    }

    @ExceptionHandler({CreditCardBusinessRuleException.class})
    private ResponseEntity<Object> handleApiException(CreditCardBusinessRuleException exception, WebRequest request) {

        ExceptionResponse response = exceptionResponse(
                exception.getMessage().toString(),
                exception.getCode(),
                exception.getStatus(),
                exception.getError());

        return handleExceptionInternal(exception, response, headers(), HttpStatus.valueOf(exception.getCode()), request);
    }

    @ExceptionHandler({InvestimentBusinessRuleException.class})
    private ResponseEntity<Object> handleApiException(InvestimentBusinessRuleException exception, WebRequest request) {

        ExceptionResponse response = exceptionResponse(
                exception.getMessage().toString(),
                exception.getCode(),
                exception.getStatus(),
                exception.getError());

        return handleExceptionInternal(exception, response, headers(), HttpStatus.valueOf(exception.getCode()), request);
    }

    @ExceptionHandler({CreditCardBusinessRuleException.class})
    private ResponseEntity<Object> handleApiException(PixBusinessRuleException exception, WebRequest request) {

        ExceptionResponse response = exceptionResponse(
                exception.getMessage().toString(),
                exception.getCode(),
                exception.getStatus(),
                exception.getError());

        return handleExceptionInternal(exception, response, headers(), HttpStatus.valueOf(exception.getCode()), request);
    }
}
