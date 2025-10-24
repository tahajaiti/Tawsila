package com.kyojin.tawsila.service.impl;

import com.kyojin.tawsila.dto.DeliveryDTO;
import com.kyojin.tawsila.enums.DeliveryStatus;
import com.kyojin.tawsila.exception.BadRequestException;
import com.kyojin.tawsila.exception.NotFoundException;
import com.kyojin.tawsila.mapper.DeliveryMapper;
import com.kyojin.tawsila.repository.DeliveryRepository;
import com.kyojin.tawsila.service.DeliveryService;

import java.util.List;
import java.util.Optional;

public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        var deliveryEntity = deliveryMapper.toEntity(deliveryDTO);
        var savedEntity = deliveryRepository.save(deliveryEntity);
        return deliveryMapper.toDTO(savedEntity);
    }

    @Override
    public Optional<DeliveryDTO> getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .map(deliveryMapper::toDTO);
    }

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(deliveryMapper::toDTO)
                .toList();
    }

    @Override
    public DeliveryDTO updateDelivery(Long deliveryId, DeliveryDTO deliveryDetails) {
        var deliveryEntity = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundException("Delivery not found with id: " + deliveryId));

        deliveryMapper.updateEntityFromDTO(deliveryDetails, deliveryEntity);

        var updatedEntity = deliveryRepository.save(deliveryEntity);
        return deliveryMapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteDelivery(Long id) {
        deliveryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Delivery not found with id: " + id));

        deliveryRepository.deleteById(id);
    }


    @Override
    public DeliveryDTO updateDeliveryStatus(Long deliveryId, String status) {
        var deliveryEntity = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundException("Delivery not found with id: " + deliveryId));

        try {
            deliveryEntity.setStatus(DeliveryStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status value: " + status);
        }

        var updatedEntity = deliveryRepository.save(deliveryEntity);
        return deliveryMapper.toDTO(updatedEntity);
    }

}
