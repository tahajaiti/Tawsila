package com.kyojin.tawsila.dto;

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
    private Double latitude;
    private Double longitude;
    private Double weightKg;
    private Double volumeM3;
    private String timeSlot; // ex: "09:00-12:00"
    private String status;

}
