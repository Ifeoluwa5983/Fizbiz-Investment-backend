package com.fizbiz.backend.repositories;

import com.fizbiz.backend.models.Investment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InvestmentRepository extends MongoRepository<Investment, String> {

    List<Investment> findAllByUserId(String userId);

}
