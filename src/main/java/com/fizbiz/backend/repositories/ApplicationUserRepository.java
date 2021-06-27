package com.fizbiz.backend.repositories;

import com.fizbiz.backend.models.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String> {

    ApplicationUser findByEmailAddress(String emailAddress);
}
