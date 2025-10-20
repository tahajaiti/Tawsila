package com.kyojin.tawsila.dto;

import com.kyojin.tawsila.enums.VehicleType;
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
    private VehicleType type;
    private double maxWeightKg;
    private double maxVolumeM3;
    private int maxDeliveries;

}