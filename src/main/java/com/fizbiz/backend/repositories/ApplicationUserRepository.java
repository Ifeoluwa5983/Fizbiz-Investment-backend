package com.fizbiz.backend.repositories;

import com.fizbiz.backend.models.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String> {

    ApplicationUser findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String email);

    ApplicationUser findByToken(String token);

    default void deactivateById(String id){
        ApplicationUser applicationUser = findById(id).orElse(null);
        assert applicationUser != null;
        applicationUser.setIsActive(false);
        applicationUser.setModifiedDate(LocalDateTime.now().toString());
        save(applicationUser);
    }
}
