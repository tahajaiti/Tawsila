package com.kyojin.tawsila.optimizer;

import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Vehicle;
import com.kyojin.tawsila.entity.Warehouse;

import java.util.List;

public interface TourOptimizer {

    /**
     * Calculates an optimized list of deliveries for a *single vehicle*.
     *
     * @param warehouse The starting/ending point (depot).
     * @param deliveries The list of all deliveries to be considered.
     * @param vehicle    The specific vehicle that will perform this tour.
     * @return An ordered list of deliveries that respects the vehicle's constraints.
     */
    List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries, Vehicle vehicle);
}