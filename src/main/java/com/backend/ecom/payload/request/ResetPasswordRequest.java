package com.backend.ecom.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank
    private String email;

    @Min(100000)
    @Max(999999)
    private int resetCode;
}
