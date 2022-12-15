package com.backend.ecom.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 5, max = 80)
    private String fullName;

    @NotBlank
    @Size(min = 2, max = 80)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 150)
    private String password;

    @NotBlank
    private String address;
}
