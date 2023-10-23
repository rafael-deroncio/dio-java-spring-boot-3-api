package com.bank.core.utils;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.ClientBusinessRuleException;
import com.bank.core.exceptions.UserBusinessRuleException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class FormatterUtil {
    public String formatCpf(String cpf) {
        cpf = cpf.trim().replaceAll("[^0-9]", "");

        int length = cpf.length();

        if (length < 11) {
            StringBuilder formattedCpf = new StringBuilder("0".repeat(11 - length));
            formattedCpf.append(cpf);
            cpf = formattedCpf.toString();
        }

        if (cpf.length() == 11) {
            return cpf;
        }

        throw new UserBusinessRuleException(String.format("cpf '%s' is not valid!", cpf), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
    }



    public String formatUsername(String username) {
        return username.trim().toLowerCase();
    }

    public String formatTelephone(String telephone) {
        String numericOnly = telephone.replaceAll("[^0-9]", "");
        int length = numericOnly.length();

        if (length < 11) {
            numericOnly = numericOnly + "0".repeat(11 - length);
        } else if (length > 11) {
            numericOnly = numericOnly.substring(0, 11);
        }

        return numericOnly;
    }


    public String formatAndValidateEmail(String email) {
        if (email == null || !EmailValidator.getInstance().isValid(email)) {
            throw new UserBusinessRuleException(String.format("Email '%s' is not valid!", email), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }
        return email.trim().toLowerCase();
    }


    public String fortamtCep(String cep) {
        cep = cep.trim().replace("-", "");
        cep = String.format("%08d", Integer.parseInt(cep));
        cep = cep.substring(0, 5) + "-" + cep.substring(5, 8);

        if (Pattern.matches("[0-9]{5}-[0-9]{3}", cep)) {
            return cep.replace("-", "");
        } else {
            throw new ClientBusinessRuleException(String.format("invalid CEP format '%s'", cep), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }
    }

    public String formatExpyresDate(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        return sdf.format(data);
    }

    public String formatCreditCardNumber(String creditCardNumber) {
        int length = creditCardNumber.length();

        if (length >= 4) {
            String lastFourDigits = creditCardNumber.substring(length - 4);
            String mask = "XXXX XXXX XXXX ";
            return mask + lastFourDigits;
        } else {
            return creditCardNumber;
        }
    }
}
