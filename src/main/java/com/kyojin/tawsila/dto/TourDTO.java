package com.kyojin.tawsila.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourDTO {

    private Long id;
    private LocalDate date;

    private VehicleDTO vehicle;

    private List<DeliveryDTO> deliveries;
}
