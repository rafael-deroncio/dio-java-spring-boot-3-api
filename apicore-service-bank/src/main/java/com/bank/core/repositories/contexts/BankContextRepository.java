package com.bank.core.repositories.contexts;

import com.bank.core.models.BankModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankContextRepository extends JpaRepository<BankModel, Integer> {
    @Query("SELECT b FROM BankModel b " +
            "JOIN FETCH b.agencies")
    BankModel getBank();
}
