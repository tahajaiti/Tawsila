package com.kyojin.tawsila.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {

    private Long id;

    @NotNull(message = "Latitude is required")
    private Double latitude;
    @NotNull(message = "Longitude is required")
    private Double longitude;
    @NotNull(message = "Weight in kg is required")
    private Double weightKg;
    @NotNull(message = "Volume in m3 is required")
    private Double volumeM3;
    private String timeSlot; // ex: "09:00-12:00"
    private String status;

}
