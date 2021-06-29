package com.fizbiz.backend.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordDto {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String userId;
}
