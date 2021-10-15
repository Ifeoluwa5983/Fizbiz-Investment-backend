package com.fizbiz.backend.repositories;

import com.fizbiz.backend.models.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
