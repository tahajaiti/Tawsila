package com.kyojin.tawsila.mapper;

import com.kyojin.tawsila.dto.request.VehicleCreateDTO;
import com.kyojin.tawsila.dto.response.VehicleDTO;
import com.kyojin.tawsila.entity.Vehicle;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleDTO toDTO(Vehicle vehicle);
    Vehicle toEntity(VehicleDTO vehicleDTO);
    Vehicle fromRequest(VehicleCreateDTO vehicleCreateDTO);

    @AfterMapping
    default void initVehicle(@MappingTarget Vehicle vehicle) {
        if (vehicle.getType() != null) {
            var type = vehicle.getType();
            vehicle.setMaxWeightKg(type.getMaxWeightKg());
            vehicle.setMaxVolumeM3(type.getMaxVolumeM3());
            vehicle.setMaxDeliveries(type.getMaxDeliveries());
        }
    }
}
