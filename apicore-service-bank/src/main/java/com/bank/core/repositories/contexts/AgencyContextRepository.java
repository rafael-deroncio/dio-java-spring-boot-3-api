package com.bank.core.repositories.contexts;

import com.bank.core.models.AgencyModel;
import com.bank.core.models.BankModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyContextRepository extends JpaRepository<AgencyModel, Integer> {
}
