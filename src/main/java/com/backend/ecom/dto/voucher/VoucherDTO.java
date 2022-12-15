package com.backend.ecom.dto.voucher;


import com.backend.ecom.entities.Voucher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
public class VoucherDTO {
    private long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private double reductionPercentage;

    private double maxReduction;

    public VoucherDTO(Voucher v) {
        this.id = v.getId();
        this.name = v.getName();
        this.description = v.getDescription();
        this.startDate = v.getStartDate();
        this.endDate = v.getEndDate();
        this.reductionPercentage = v.getReductionPercentage();
        this.maxReduction = v.getMaxReduction();
    }
}
