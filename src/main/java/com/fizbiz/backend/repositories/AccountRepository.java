package com.fizbiz.backend.repositories;

import com.fizbiz.backend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
