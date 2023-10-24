package com.bank.core.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
@Component
public class BankOperationsUtil {
    private static final BigDecimal BASE_LIMIT = new BigDecimal("1000.00");
    private static final BigDecimal INCOME_MULTIPLIER = new BigDecimal("0.05");
    private static final BigDecimal SCORE_MULTIPLIER = new BigDecimal("0.1");

    private static final Integer EXPIRES_YEAR = 3;
    private static final String MODEL_EXPIRES = "MM/yyyy";

    public BigDecimal calculateCreditLimit(BigDecimal income, double creditScore) {
        return BASE_LIMIT.add(income.multiply(INCOME_MULTIPLIER))
                .add(BigDecimal.valueOf(creditScore).multiply(SCORE_MULTIPLIER))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public String generateCreditCardNumber() {
        StringBuilder creditCardNumber = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int group = 1000 + random.nextInt(9000);
            creditCardNumber.append(group);
            if (i < 3) {
                creditCardNumber.append(" ");
            }
        }

        return creditCardNumber.toString();
    }

    public String generateCVV() {
        Random random = new Random();
        int cvv = 100 + random.nextInt(900);

        return String.valueOf(cvv);
    }

    public Date generateExpiryDate() {
        Random random = new Random();
        int yearsToAdd = EXPIRES_YEAR + random.nextInt(EXPIRES_YEAR);

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.YEAR, yearsToAdd);

        return calendar.getTime();
    }

    public String getExpiryDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(MODEL_EXPIRES);
        String expiryDate = dateFormat.format(date);

        return expiryDate;
    }
}
