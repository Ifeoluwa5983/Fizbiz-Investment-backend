package com.fizbiz.backend.security;

import com.fizbiz.backend.models.ApplicationUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private ApplicationUser user;

    private String token;
}
