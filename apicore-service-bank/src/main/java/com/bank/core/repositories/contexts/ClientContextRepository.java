package com.bank.core.repositories.contexts;

import com.bank.core.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ClientContextRepository extends JpaRepository<ClientModel, Integer> {

    @Query("SELECT c FROM ClientModel c WHERE c.cpf = :cpf")
    ClientModel findByUsername(@Param("cpf") String cpf);

}
