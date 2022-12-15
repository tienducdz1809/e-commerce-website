package com.backend.ecom.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateInfoRequestDTO {
    @Size(min = 10, max = 100)
    private String fullName;

    @Size(min = 2, max = 100)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    private String role;

    private String address;

}
