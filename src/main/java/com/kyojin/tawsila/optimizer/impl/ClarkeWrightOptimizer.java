package com.kyojin.tawsila.optimizer.impl;

import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Vehicle;
import com.kyojin.tawsila.entity.Warehouse;
import com.kyojin.tawsila.enums.VehicleType;
import com.kyojin.tawsila.optimizer.TourOptimizer;
import com.kyojin.tawsila.util.DistanceCalculator;
import lombok.RequiredArgsConstructor;

import java.util.*;

public class ClarkeWrightOptimizer implements TourOptimizer {


    @RequiredArgsConstructor
    private static class Saving implements Comparable<Saving> {
        final Delivery from;
        final Delivery to;
        final double amount; // how much distance saved

        @Override
        public int compareTo(Saving other) {
            // we sort in descending order of savings
            return Double.compare(other.amount, this.amount);
        }
    }

    private static class SubTour {
        LinkedList<Delivery> deliveries = new LinkedList<>(); // use linked list because THEY ARE LINKED and fast
        double currentWeight = 0.0;
        double currentVolume = 0.0;

        SubTour(Delivery del) {
            this.deliveries.add(del);
            this.currentWeight = del.getWeightKg();
            this.currentVolume = del.getVolumeM3();
        }

        Delivery getFirst() { return deliveries.getFirst(); }
        Delivery getLast()  { return deliveries.getLast(); }
        int getSize()  { return deliveries.size(); }
    }




}
