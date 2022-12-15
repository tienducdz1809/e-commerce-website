package com.backend.ecom.entities;

import com.backend.ecom.dto.voucher.VoucherRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.tomcat.jni.Local;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String description;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    private double reductionPercentage;

    private double maxReduction;

    public Voucher(VoucherRequestDTO v) {
        this.name = v.getName();
        this.description = v.getDescription();
        this.startDate = v.getStartDate();
        this.endDate = v.getEndDate();
        this.reductionPercentage = v.getReductionPercentage();
        this.maxReduction = v.getMaxReduction();
    }

    public static double applyVoucher(double price, Voucher voucher){
        double finalPrice = price;
        double reducedPrice = price * voucher.getReductionPercentage();

        if (reducedPrice > voucher.getMaxReduction()){
            finalPrice = finalPrice - voucher.getMaxReduction();
        } else {
            finalPrice = finalPrice - reducedPrice;
        }
        return  finalPrice;
    }

}
