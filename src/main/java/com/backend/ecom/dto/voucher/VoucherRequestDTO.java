package com.backend.ecom.dto.voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class VoucherRequestDTO {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull
    @Max(100)
    private double reductionPercentage;

    @NotNull
    private double maxReduction;
}
