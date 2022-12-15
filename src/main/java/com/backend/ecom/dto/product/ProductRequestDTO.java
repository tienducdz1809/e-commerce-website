package com.backend.ecom.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {
    private String thumbnail;

    @Size(min = 2)
    private String name;

    @NotBlank
    private String description;

    @Min(1)
    private Integer quantity;

    @DecimalMin("100.0")
    private Double price;

    @NotEmpty
    private Long[] categoryIds;

    @Min(1)
    private Integer brandId;

    @NotBlank
    private String screen;

    @NotBlank
    private String os;

    @NotBlank
    private String camera;

    @NotBlank
    private String processor;

    @Min(2)
    private Integer ram;

    @Min(16)
    private Integer internalStorage;

    @Min(500)
    private Integer battery;
}
