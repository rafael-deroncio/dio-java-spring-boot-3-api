package com.bank.core.repositories.contexts;

import com.bank.core.models.CreditCardModel;
import com.bank.core.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardContextRepository extends JpaRepository<CreditCardModel, Integer> {

    @Query("SELECT c FROM CreditCardModel c " +
            "JOIN FETCH c.creditCardTransactions " +
            "LEFT JOIN FETCH c.account a " +
            "WHERE a.id = :accountNumber")
    CreditCardModel findByAccountNumber(Integer accountNumber);


}


