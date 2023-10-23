package com.bank.core.repositories.contexts;

import com.bank.core.models.AgencyAddressModel;
import com.bank.core.models.AgencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyAddressContextRepository extends JpaRepository<AgencyAddressModel, Integer> {
}
