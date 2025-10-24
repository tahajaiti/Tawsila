package com.kyojin.tawsila.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeliveryStatusDTO {
    @NotNull
    private String status;
}
