package com.bank.core.repositories.contexts;

import com.bank.core.models.InvestmentIncomesModel;
import com.bank.core.models.InvestmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentContextRepository extends JpaRepository<InvestmentModel,Integer> {
}
