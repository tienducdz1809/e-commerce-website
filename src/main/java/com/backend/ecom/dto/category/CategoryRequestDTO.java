package com.backend.ecom.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDTO {
    @Size(min = 2, max = 20)
    private String name;
}
