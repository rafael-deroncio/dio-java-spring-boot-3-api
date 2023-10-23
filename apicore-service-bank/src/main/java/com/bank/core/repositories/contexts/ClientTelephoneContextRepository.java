package com.bank.core.repositories.contexts;

import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientTelephoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTelephoneContextRepository extends JpaRepository<ClientTelephoneModel, Integer> {
}
