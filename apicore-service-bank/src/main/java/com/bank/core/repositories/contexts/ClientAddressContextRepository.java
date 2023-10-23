package com.bank.core.repositories.contexts;

import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientAddressContextRepository extends JpaRepository<ClientAddressModel, Integer> {

    @Query("SELECT c FROM ClientAddressModel c WHERE c.client.id = :clientId")
    List<ClientAddressModel> getClientAddress(@Param("clientId") int clientId);
}
