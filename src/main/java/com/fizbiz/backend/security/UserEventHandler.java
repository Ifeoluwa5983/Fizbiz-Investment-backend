package com.fizbiz.backend.security;

import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.services.ApplicationUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RepositoryEventHandler
public class UserEventHandler {

    @Autowired
    ApplicationUserServiceImpl applicationUserServiceImpl;

    @HandleBeforeCreate
    public void handleBeforeCreate(ApplicationUser applicationUser)  {
        log.info("User object -> {}", applicationUser);
    }
}
