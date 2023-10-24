package com.bank.core.repositories.contexts;

import com.bank.core.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserContextRepository extends JpaRepository<UserModel, Integer> {

    @Query("SELECT u FROM UserModel u " +
            "JOIN FETCH u.client " +
            "WHERE u.username = :username")
    UserModel findUserByUsername(@Param("username") String username);

    @Query("SELECT c FROM UserModel c " +
            "JOIN FETCH c.client " +
            "WHERE c.id = :id")
    UserModel findUserById(@Param("id") Integer id);
}
