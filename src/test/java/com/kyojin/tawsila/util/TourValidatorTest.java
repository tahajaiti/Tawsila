package com.kyojin.tawsila.util;

import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Tour;
import com.kyojin.tawsila.entity.Vehicle;
import com.kyojin.tawsila.enums.VehicleType;
import com.kyojin.tawsila.exception.CapacityExceededException;
import com.kyojin.tawsila.exception.MaxDeliveriesExceededException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TourValidatorTest {

    @Test
    public void testValidateCapacity() {
        Vehicle vehicle = new Vehicle(VehicleType.VAN);
        vehicle.setMaxWeightKg(1000);
        vehicle.setMaxVolumeM3(8);
        vehicle.setMaxDeliveries(50);

        Delivery delivery1 = new Delivery();
        delivery1.setWeightKg(200.0);
        delivery1.setVolumeM3(1.5);

        Delivery delivery2 = new Delivery();
        delivery2.setWeightKg(300.0);
        delivery2.setVolumeM3(2.0);

        Tour tour = new Tour();
        tour.setVehicle(vehicle);
        tour.setDeliveries(List.of(delivery1, delivery2));

        Assertions.assertDoesNotThrow(() -> TourValidator.validateCapactity(tour));
    }

    @Test
    public void testValidateCapacity_ExceedsWeight() {
        Vehicle vehicle = new Vehicle(VehicleType.VAN);
        vehicle.setMaxWeightKg(200);
        vehicle.setMaxVolumeM3(8);
        vehicle.setMaxDeliveries(50);

        Delivery delivery1 = new Delivery();
        delivery1.setWeightKg(200.0);
        delivery1.setVolumeM3(1.5);

        Delivery delivery2 = new Delivery();
        delivery2.setWeightKg(300.0);
        delivery2.setVolumeM3(2.0);

        Tour tour = new Tour();
        tour.setVehicle(vehicle);
        tour.setDeliveries(List.of(delivery1, delivery2));

        Assertions.assertThrows(CapacityExceededException.class, () -> TourValidator.validateCapactity(tour));
    }

    @Test
    public void testValidateCapacity_ExceedsVolume() {
        Vehicle vehicle = new Vehicle(VehicleType.VAN);
        vehicle.setMaxWeightKg(1000);
        vehicle.setMaxVolumeM3(2);
        vehicle.setMaxDeliveries(50);

        Delivery delivery1 = new Delivery();
        delivery1.setWeightKg(200.0);
        delivery1.setVolumeM3(1.5);

        Delivery delivery2 = new Delivery();
        delivery2.setWeightKg(300.0);
        delivery2.setVolumeM3(2.0);

        Tour tour = new Tour();
        tour.setVehicle(vehicle);
        tour.setDeliveries(List.of(delivery1, delivery2));

        Assertions.assertThrows(CapacityExceededException.class, () -> TourValidator.validateCapactity(tour));
    }

    @Test
    public void testValidateCapacity_ExceedsDeliveries() {
        Vehicle vehicle = new Vehicle(VehicleType.VAN);
        vehicle.setMaxWeightKg(1000);
        vehicle.setMaxVolumeM3(8);
        vehicle.setMaxDeliveries(1);

        Delivery delivery1 = new Delivery();
        delivery1.setWeightKg(200.0);
        delivery1.setVolumeM3(1.5);

        Delivery delivery2 = new Delivery();
        delivery2.setWeightKg(300.0);
        delivery2.setVolumeM3(2.0);

        Tour tour = new Tour();
        tour.setVehicle(vehicle);
        tour.setDeliveries(List.of(delivery1, delivery2));

        Assertions.assertThrows(MaxDeliveriesExceededException.class, () -> TourValidator.validateCapactity(tour));
    }
}
