package com.fizbiz.backend.repositories;

import com.fizbiz.backend.models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, String> {

    List<Investment> findAllByUserId(Long userId);

}
