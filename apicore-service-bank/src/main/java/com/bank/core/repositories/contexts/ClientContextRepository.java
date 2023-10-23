package com.bank.core.repositories.contexts;

import com.bank.core.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ClientContextRepository extends JpaRepository<ClientModel, Integer> {
    @Query("SELECT c FROM ClientModel c " +
            "JOIN FETCH c.user " +
            "LEFT JOIN FETCH c.addresses " +
            "LEFT JOIN FETCH c.telephones " +
            "WHERE c.id = :clientId")
    ClientModel getClient(@Param("clientId") int clientId);

    @Query("SELECT c FROM ClientModel c " +
            "JOIN FETCH c.user " +
            "LEFT JOIN FETCH c.telephones " +
            "WHERE c.user.id = :userId")
    ClientModel getClientByUserId(@Param("userId") int userId);
}
