package com.fizbiz.backend.repositories;

import com.fizbiz.backend.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String email);

    ApplicationUser findByToken(String token);

    default void deactivateById(Long id){
        ApplicationUser applicationUser = findById(id).orElse(null);
        assert applicationUser != null;
        applicationUser.setIsActive(false);
        applicationUser.setModifiedDate(LocalDateTime.now().toString());
        save(applicationUser);
    }
}
