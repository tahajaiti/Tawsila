package com.kyojin.tawsila.mapper;

import com.kyojin.tawsila.dto.DeliveryDTO;
import com.kyojin.tawsila.entity.Delivery;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryDTO toDTO(Delivery delivery);
    Delivery toEntity(DeliveryDTO deliveryDTO);

    @AfterMapping
    default void initDelivery(@MappingTarget Delivery delivery) {
        if (delivery.getStatus() == null) {
            delivery.setStatus(com.kyojin.tawsila.enums.DeliveryStatus.PENDING);
        }
    }
}
