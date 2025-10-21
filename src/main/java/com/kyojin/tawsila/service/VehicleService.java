package com.kyojin.tawsila.service;

import com.kyojin.tawsila.dto.request.VehicleCreateDTO;
import com.kyojin.tawsila.dto.response.VehicleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Vehicle operations.
 * Handles CRUD operations for Vehicle entities.
 */
public interface VehicleService {

    /**
     * Create a new vehicle.
     *
     * @param createDTO the DTO containing vehicle data
     * @return the created VehicleDTO
     */
    VehicleDTO createVehicle(VehicleCreateDTO createDTO);

    /**
     * Get a vehicle by its ID.
     *
     * @param id the ID of the vehicle
     * @return an Optional containing the VehicleDTO if found
     */
    Optional<VehicleDTO> getVehicleById(Long id);

    /**
     * Get all vehicles.
     *
     * @return a list of VehicleDTOs
     */
    List<VehicleDTO> getAllVehicles();

    /**
     * Update an existing vehicle.
     *
     * @param vehicleId         the ID of the vehicle to update
     * @param vehicleDetailsDTO the DTO containing updated vehicle data
     * @return the updated VehicleDTO
     */
    VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDetailsDTO);

    /**
     * Delete a vehicle by its ID.
     *
     * @param id the ID of the vehicle to delete
     */
    void deleteVehicle(Long id);
}
