package com.backend.ecom.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordAfterResetRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String newPassword;
}
