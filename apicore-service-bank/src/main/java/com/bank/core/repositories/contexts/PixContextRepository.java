package com.bank.core.repositories.contexts;

import com.bank.core.models.PixModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixContextRepository extends JpaRepository<PixModel, Integer> {
}
