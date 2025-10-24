package com.kyojin.tawsila.service;

import com.kyojin.tawsila.dto.DeliveryDTO;

import java.util.List;

public interface TourService {

    /**
     * Get optimized tour using specified algorithm
     * @param tourId ID of the tour
     * @param algorithm Algorithm to use for optimization (e.g., "NEAREST_NEIGHBOR")
     * @return DTO list representing the optimized tour
     */
    List<DeliveryDTO> getOptimizedTour(Long tourId, String algorithm);

    /**
     * Calculate total distance of the tour
     * @param tourId ID of the tour
     * @return Total distance of the tour in kilometers
     */
    double getTotalDistance(Long tourId);
}
