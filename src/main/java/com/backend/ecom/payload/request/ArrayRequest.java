package com.backend.ecom.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ArrayRequest {
    @NotNull
    private Long[] ids;
}
