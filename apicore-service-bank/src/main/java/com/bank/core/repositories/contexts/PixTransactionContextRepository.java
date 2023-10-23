package com.bank.core.repositories.contexts;

import com.bank.core.models.PixTransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixTransactionContextRepository extends JpaRepository<PixTransactionModel, Integer> {
}
