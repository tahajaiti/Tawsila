package com.kyojin.tawsila.enums;

import lombok.Getter;

@Getter
public enum VehicleType {
    BIKE(50, 0.5, 15),
    VAN(1000, 8, 50),
    TRUCK(5000, 40, 100);

    private final double maxWeightKg;
    private final double maxVolumeM3;
    private final int maxDeliveries;

    VehicleType(double maxWeightKg, double maxVolumeM3, int maxDeliveries) {
        this.maxWeightKg = maxWeightKg;
        this.maxVolumeM3 = maxVolumeM3;
        this.maxDeliveries = maxDeliveries;
    }


    /**
     * Check if the vehicle type can handle the given weight, volume, and number of deliveries.
     * @param weightKg weight in kilograms
     * @param volumeM3 volume in cubic meters
     * @param deliveries number of deliveries
     * @return true if the vehicle type can handle the given parameters, false otherwise.
     */
    public boolean canHandle(double weightKg, double volumeM3, int deliveries) {
        return weightKg <= maxWeightKg && volumeM3 <= maxVolumeM3 && deliveries <= maxDeliveries;
    }

    public boolean canHandle(double weightKg, double volumeM3) {
        return weightKg <= maxWeightKg && volumeM3 <= maxVolumeM3;
    }
}
