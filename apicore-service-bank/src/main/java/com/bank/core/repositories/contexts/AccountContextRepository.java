package com.bank.core.repositories.contexts;

import com.bank.core.models.AccountModel;
import com.bank.core.models.AgencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountContextRepository extends JpaRepository<AccountModel, Integer> {
    @Query("SELECT DISTINCT a FROM AccountModel a " +
            "LEFT JOIN FETCH a.transactions t " +
            "WHERE a.codClient = :clientId")
    AccountModel getAccountByClientId(@Param("clientId") Integer clientId);

    @Query("SELECT DISTINCT a FROM AccountModel a LEFT JOIN FETCH a.transactions t")
    List<AccountModel> getAccounts();

    @Query("SELECT DISTINCT a FROM AccountModel a LEFT JOIN FETCH a.transactions t WHERE a.codAgency = :accountAgency")
    List<AccountModel> getAccountsByAgencyNumber(@Param("accountAgency")Integer accountAgency);

    @Query("SELECT account FROM AccountModel account " +
            "WHERE account.codAgency = :agencyId " +
            "AND account.id = :accountId")
    AccountModel getAccount(@Param("agencyId") Integer agencyId, @Param("accountId") Integer accountId);

}
