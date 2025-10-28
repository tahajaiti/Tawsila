package com.kyojin.tawsila.service;

import com.kyojin.tawsila.dto.DeliveryDTO;
import com.kyojin.tawsila.dto.TourDTO;
import com.kyojin.tawsila.dto.TourDistanceDTO;

import java.util.List;
import java.util.Optional;

public interface TourService {

    /**
     * Create a new tour
     * @param dto Data transfer object containing tour details
     * @return Created tour DTO
     */
    TourDTO createTour(TourDTO dto);

    /**
     * Retrieve a tour by its ID
     * @param id ID of the tour
     * @return Optional containing the tour DTO if found, empty otherwise
     */
    Optional<TourDTO> getTourById(Long id);

    /**
     * Retrieve all tours
     * @return List of all tour DTOs
     */
    List<TourDTO> getAllTours();

    /**
     * Update an existing tour
     * @param id ID of the tour to update
     * @param dto Data transfer object containing updated tour details
     * @return Updated tour DTO
     */
    TourDTO updateTour(Long id, TourDTO dto);

    /**
     * Delete a tour by its ID
     * @param id ID of the tour to delete
     */
    void deleteTour(Long id);

    /**
     * Get optimized tour using specified algorithm
     * @param tourId ID of the tour
     * @param algorithm Algorithm to use for optimization (e.g., "NEAREST_NEIGHBOR")
     * @return TourDTO with optimized route
     */
    TourDTO getOptimizedTour(Long tourId, String algorithm);

    /**
     * Calculate total distance of the tour
     * @param tourId ID of the tour
     * @return Total distance of the tour in kilometers
     */
    TourDistanceDTO getTotalDistance(Long tourId);
}
