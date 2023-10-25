package com.bank.core.utils;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.ClientBusinessRuleException;
import com.bank.core.exceptions.UserBusinessRuleException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Utility class for formatting and validating various types of data.
 */
@Component
public class FormatterUtil {

    private final Date TODAY = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    /**
     * Formats a CPF (Cadastro de Pessoas Físicas) number by removing non-numeric characters,
     * adding leading zeros, and checking for a valid length.
     *
     * @param cpf The CPF to be formatted.
     * @return The formatted CPF.
     * @throws UserBusinessRuleException if the CPF is not valid.
     */
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

    /**
     * Formats a username by trimming and converting it to lowercase.
     *
     * @param username The username to be formatted.
     * @return The formatted username.
     */
    public String formatUsername(String username) {
        return username.trim().toLowerCase();
    }

    /**
     * Formats a telephone number by removing non-numeric characters, adjusting its length,
     * and ensuring it's a valid length.
     *
     * @param telephone The telephone number to be formatted.
     * @return The formatted telephone number.
     */
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

    /**
     * Formats and validates an email address by trimming, converting it to lowercase,
     * and checking for a valid email format.
     *
     * @param email The email address to be formatted and validated.
     * @return The formatted and validated email address.
     * @throws UserBusinessRuleException if the email is not valid.
     */
    public String formatAndValidateEmail(String email) {
        if (email == null || !EmailValidator.getInstance().isValid(email)) {
            throw new UserBusinessRuleException(String.format("Email '%s' is not valid!", email), HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }
        return email.trim().toLowerCase();
    }

    /**
     * Formats a CEP (Código de Endereçamento Postal) by removing non-numeric characters,
     * ensuring it has a valid format, and returning it without hyphens.
     *
     * @param cep The CEP to be formatted.
     * @return The formatted CEP without hyphens.
     * @throws ClientBusinessRuleException if the CEP is not in a valid format.
     */
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

    /**
     * Formats a Date object as a string in "MM/yy" (month/year) format.
     *
     * @param data The Date object to be formatted.
     * @return The formatted date string.
     */
    public String formatExpyresDate(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        return sdf.format(data);
    }

    /**
     * Masks a credit card number, showing only the last four digits.
     *
     * @param creditCardNumber The credit card number to be masked.
     * @return The masked credit card number.
     */
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

    public Date getTodayDate() {
        return TODAY;
    }
}
