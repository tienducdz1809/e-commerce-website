package com.backend.ecom.dto.feedback;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackRequestDTO {
    @NotBlank
    private String content;

    @Min(1)
    @Max(5)
    private Integer rating;
}
