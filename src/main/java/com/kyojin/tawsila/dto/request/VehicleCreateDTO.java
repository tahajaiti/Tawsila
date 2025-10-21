package com.kyojin.tawsila.dto.request;

import com.kyojin.tawsila.enums.VehicleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleCreateDTO {

    @NotNull(message = "Vehicle type is required")
    private String type;
}
