package com.backend.ecom.dto.brand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class BrandRequestDTO {
    @NotNull
    @Size(min = 2, max = 20)
    private String name;
}
