package com.kyojin.tawsila.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;

    @NotNull(message = "Vehicle type is required")
    private String type;
    private double maxWeightKg;
    private double maxVolumeM3;
    private int maxDeliveries;

}