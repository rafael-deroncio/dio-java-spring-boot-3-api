package com.bank.core.repositories.contexts;

import com.bank.core.models.InvestmentIncomesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentIncomesContextRepository extends JpaRepository<InvestmentIncomesModel,Integer> {
}
