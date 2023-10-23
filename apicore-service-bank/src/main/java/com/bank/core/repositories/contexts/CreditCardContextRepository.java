package com.bank.core.repositories.contexts;

import com.bank.core.models.CreditCardModel;
import com.bank.core.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardContextRepository extends JpaRepository<CreditCardModel, Integer> {
}
