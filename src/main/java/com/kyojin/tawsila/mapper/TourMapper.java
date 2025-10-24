package com.kyojin.tawsila.mapper;

import com.kyojin.tawsila.dto.TourDTO;
import com.kyojin.tawsila.entity.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {DeliveryMapper.class, VehicleMapper.class})
public interface TourMapper {

    TourDTO toDTO(Tour tour);

    Tour toEntity(TourDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TourDTO dto, @MappingTarget Tour entity);
}
