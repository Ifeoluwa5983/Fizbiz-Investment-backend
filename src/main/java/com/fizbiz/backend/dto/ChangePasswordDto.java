package com.fizbiz.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ChangePasswordDto {

    @NonNull
    private String oldPassword;

    @NonNull
    private String newPassword;

    @NonNull
    private Long userId;
}
