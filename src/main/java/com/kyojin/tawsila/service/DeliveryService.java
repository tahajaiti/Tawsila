package com.kyojin.tawsila.service;

import com.kyojin.tawsila.dto.DeliveryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Delivery operations.
 * Handles CRUD operations for Delivery entities.
 */
public interface DeliveryService {

    /**
     * Create a new delivery.
     *
     * @param dto the DTO containing delivery data
     * @return the created DeliveryResponseDTO
     */
    DeliveryDTO createDelivery(DeliveryDTO dto);

    /**
     * Get a delivery by its ID.
     *
     * @param id the ID of the delivery
     * @return an Optional containing the DeliveryResponseDTO if found
     */
    Optional<DeliveryDTO> getDeliveryById(Long id);

    /**
     * Get all deliveries.
     *
     * @return a list of DeliveryResponseDTOs
     */
    List<DeliveryDTO> getAllDeliveries();

    /**
     * Update an existing delivery.
     *
     * @param deliveryId      the ID of the delivery to update
     * @param deliveryDetails the DTO containing updated delivery data
     * @return the updated DeliveryResponseDTO
     */
    DeliveryDTO updateDelivery(Long deliveryId, DeliveryDTO deliveryDetails);

    /**
     * Delete a delivery by its ID.
     *
     * @param id the ID of the delivery to delete
     */
    void deleteDelivery(Long id);


    /**
     * Update the status of an existing delivery.
     *
     * @param deliveryId the ID of the delivery to update
     * @param status     the new status of the delivery
     * @return the updated DeliveryDTO
     */
    DeliveryDTO updateDeliveryStatus(Long deliveryId, String status);
}
