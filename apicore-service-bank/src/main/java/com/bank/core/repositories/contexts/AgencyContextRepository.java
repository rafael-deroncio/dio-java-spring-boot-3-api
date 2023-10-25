package com.bank.core.repositories.contexts;

import com.bank.core.models.AgencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyContextRepository extends JpaRepository<AgencyModel, Integer> {
}
