package com.bank.core.repositories.contexts;

import com.bank.core.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountContextRepository extends JpaRepository<AccountModel, Integer> {
}
