package com.kyojin.tawsila.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Tour date is required")
    @FutureOrPresent(message = "Tour date cannot be in the past")
    private LocalDate date;

    @NotNull(message = "Vehicle information is required")
    private VehicleDTO vehicle;

    @NotEmpty(message = "Tour must have at least one delivery")
    private List<DeliveryDTO> deliveries;
}
