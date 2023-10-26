package com.bank.core.repositories.contexts;

import com.bank.core.models.AccountModel;
import com.bank.core.models.PixModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PixContextRepository extends JpaRepository<PixModel, Integer> {

    @Query("SELECT p FROM PixModel p " +
            "WHERE p.codAgency = :codAgency " +
            "AND p.codAccount = :codAccount")
    PixModel findByAccount(@Param("codAgency") Integer codAgency, @Param("codAccount") Integer codAccount);

    @Query("SELECT p FROM PixModel p " +
            "JOIN FETCH p.pixDetails d " +
            "WHERE d.pixKey = :key")
    PixModel findBykey(@Param("key") String key);


}
