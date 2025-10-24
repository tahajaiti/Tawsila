package com.kyojin.tawsila.optimizer.impl;

import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Warehouse;
import com.kyojin.tawsila.optimizer.TourOptimizer;
import com.kyojin.tawsila.util.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborOptimizer implements TourOptimizer {
    @Override
    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries) {
        if (deliveries == null || deliveries.isEmpty()) return new ArrayList<>();

        // two lists to keep track of optimized and not visited deliveries
        List<Delivery> optimized = new ArrayList<>();
        List<Delivery> notVisited = new ArrayList<>(deliveries);

        // we start from the warehouse position
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        while (!notVisited.isEmpty()) {
            Delivery nearestDelivery = notVisited.get(0);

            // we get the minimum distance delivery from the current position
            double minDistance = DistanceCalculator.calculateDistance(
                    currentLat, currentLon,
                    nearestDelivery.getLatitude(), nearestDelivery.getLongitude()
            );

            for (Delivery delivery: notVisited) {
                // calculate distance from current position to delivery
                double dist = DistanceCalculator.calculateDistance(
                        currentLat, currentLon,
                        delivery.getLatitude(), delivery.getLongitude()
                );

                // update nearest delivery if found a closer one
                if (dist < minDistance) {
                    minDistance = dist;
                    nearestDelivery = delivery;
                }
            }

            optimized.add(nearestDelivery);
            notVisited.remove(nearestDelivery);
            
            // update current position to the last added delivery
            currentLat = nearestDelivery.getLatitude();
            currentLon = nearestDelivery.getLongitude();
        }

        return optimized;
    }
}
