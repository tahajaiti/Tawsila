package com.kyojin.tawsila.mapper;

import com.kyojin.tawsila.dto.request.VehicleCreateDTO;
import com.kyojin.tawsila.dto.response.VehicleDTO;
import com.kyojin.tawsila.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    VehicleDTO toDTO(Vehicle vehicle);
    Vehicle toEntity(VehicleDTO vehicleDTO);
    Vehicle fromRequest(VehicleCreateDTO vehicleCreateDTO);
}
