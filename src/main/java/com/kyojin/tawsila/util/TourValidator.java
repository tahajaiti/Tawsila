package com.kyojin.tawsila.util;

import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Tour;
import com.kyojin.tawsila.enums.VehicleType;
import com.kyojin.tawsila.exception.CapacityExceededException;
import com.kyojin.tawsila.exception.MaxDeliveriesExceededException;

public class TourValidator {

    public static void validateCapactity(Tour tour) {
        var vehicle = tour.getVehicle();
        var deliveries = tour.getDeliveries();

        if (deliveries == null || deliveries.isEmpty()) {
            return;
        }

        double totalWeight = deliveries.stream()
                .mapToDouble(Delivery::getWeightKg)
                .sum();

        double totalVolume = deliveries.stream()
                .mapToDouble(Delivery::getVolumeM3)
                .sum();

        int totalDeliveries = deliveries.size();

//        if (totalWeight > vehicle.getMaxWeightKg()) {
//            throw new CapacityExceededException("Total weight of deliveries " + totalWeight +
//                    " kg exceeds vehicle's maximum weight capacity " + vehicle.getMaxWeightKg() + " kg.");
//        }
//
//        if (totalVolume > vehicle.getMaxVolumeM3()) {
//            throw new CapacityExceededException("Total volume of deliveries " + totalVolume +
//                    " m3 exceeds vehicle's maximum volume capacity " + vehicle.getMaxVolumeM3() + " m3.");
//        }
//
//        if (totalDeliveries > vehicle.getMaxDeliveries()) {
//            throw new MaxDeliveriesExceededException("Total number of deliveries " + totalDeliveries +
//                    " exceeds vehicle's maximum delivery count " + vehicle.getMaxDeliveries() + ".");
//        }


        VehicleType type = vehicle.getType();

        if (!type.canHandle(totalWeight, totalVolume, totalDeliveries)) {
            if (totalWeight > type.getMaxWeightKg()) {
                throw new CapacityExceededException("Total weight " + totalWeight +
                        " kg exceeds " + type + " max of " + type.getMaxWeightKg() + " kg.");
            }

            if (totalVolume > type.getMaxVolumeM3()) {
                throw new CapacityExceededException("Total volume " + totalVolume +
                        " m3 exceeds " + type + " max of " + type.getMaxVolumeM3() + " m3.");
            }

            if (totalDeliveries > type.getMaxDeliveries()) {
                throw new MaxDeliveriesExceededException("Total deliveries " + totalDeliveries +
                        " exceeds " + type + " max of " + type.getMaxDeliveries() + ".");
            }
        }
    }
}
