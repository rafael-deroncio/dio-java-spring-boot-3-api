package com.bank.core.repositories.contexts;

import com.bank.core.models.AccountModel;
import com.bank.core.models.AccountTransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionContextRepository extends JpaRepository<AccountTransactionModel, Integer> {
}
