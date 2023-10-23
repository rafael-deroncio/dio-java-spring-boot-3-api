package com.bank.core.repositories.contexts;

import com.bank.core.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountContextRepository extends JpaRepository<AccountModel, Integer> {
    @Query("SELECT DISTINCT a FROM AccountModel a LEFT JOIN FETCH a.transactions t WHERE a.codClient = :clientId")
    AccountModel getAccountByClientId(@Param("clientId") Integer clientId);
}
