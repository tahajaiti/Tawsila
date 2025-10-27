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



    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries, Vehicle vehicle) {
        if (deliveries == null || deliveries.isEmpty()) return new ArrayList<>();

        VehicleType vehicleType = vehicle.getType();

        List<Delivery> notVisited = new ArrayList<>(deliveries);

        // same method from before to filter deliveries that can be handled by the vehicle
        for (Delivery del : deliveries) {
            if (vehicleType.canHandle(del.getWeightKg(), del.getVolumeM3())) {
                notVisited.add(del);
            }
        }

        // edge cases
        if (notVisited.isEmpty()) return new ArrayList<>();
        if (notVisited.size() == 1) return notVisited;

        // an array to hold all savings
        List<Saving> savings = new ArrayList<>();

        // we loop through every pair of deliveries (i, j)
        for (int i = 0; i < notVisited.size(); i++) {
            for (int j = i + 1; j < notVisited.size(); j++) {
                Delivery del1 = notVisited.get(i);
                Delivery del2 = notVisited.get(j);

                // implemnting the formula
                // saving(i,j) = (dist(warehouse, i) + dist(warehouse, j)) - dist(i, j)
                double savingAmount = dist(warehouse, del1) + dist(warehouse, del2) - dist(del1, del2);

                // we take only the positive ones
                if (savingAmount > 0) {
                    savings.add(new Saving(del1, del2, savingAmount));
                }
            }
        }

        // sort from high to low
        Collections.sort(savings);

        // initialize each delivery as its own subtour
        Map<Delivery, SubTour> tourMap = new HashMap<>();
        for (Delivery del : notVisited) {
            tourMap.put(del, new SubTour(del));
        }

        // loop through savings from highest to lowest
        for (Saving saving : savings) {
            Delivery del1 = saving.from;
            Delivery del2 = saving.to;

            // get the tours for each delivery
            SubTour tour1 = tourMap.get(del1);
            SubTour tour2 = tourMap.get(del2);

            if (tour1 == tour2) {
                // both deliveries are already in the same subtour
                continue;
            }

            // check if merging the tours would break the vehicle constraints
            double combinedWeight = tour1.currentWeight + tour2.currentWeight;
            double combinedVolume = tour1.currentVolume + tour2.currentVolume;
            int combinedStops = tour1.getSize() + tour2.getSize();

            if (!vehicleType.canHandle(combinedWeight, combinedVolume, combinedStops)) {
                continue; // cannot merge due to constraints
            }

            // check if del1 is at the end of tour1 and del2 is at the start of tour2 (or vice versa)
            // this ensures we maintain a valid route as we can only link the start/end

            if (tour1.getLast().equals(del1) && tour2.getFirst().equals(del2)) {
                mergeTours(tourMap, tour1, tour2);
            } else if (tour2.getLast().equals(del2) && tour1.getFirst().equals(del1)) {
                // merge tour1 into tour2
                mergeTours(tourMap, tour2, tour1);
            } else if (tour1.getFirst().equals(del1) && tour2.getLast().equals(del2)) {
                // reverse tour1 and merge
                Collections.reverse(tour1.deliveries);
                mergeTours(tourMap, tour1, tour2);
            } else if (tour2.getFirst().equals(del2) && tour1.getLast().equals(del1)) {
                // reverse tour2 and merge
                Collections.reverse(tour2.deliveries);
                mergeTours(tourMap, tour1, tour2);
            }
        }

        // after merging, we find the tour with most delivires

        SubTour bestTour = null;
        int maxStops = 0;

        Set<SubTour> finalTour = new HashSet<>(tourMap.values());

        for (SubTour tour : finalTour) {
            if (tour.getSize() > maxStops) {
                maxStops = tour.getSize();
                bestTour = tour;
            }
        }


        return (bestTour != null) ? bestTour.deliveries : new ArrayList<>();
    }

    /**
     * Calculates distance between Warehouse and a Delivery.
     */
    private double dist(Warehouse w, Delivery d) {
        return DistanceCalculator.calculateDistance(
                w.getLatitude(), w.getLongitude(),
                d.getLatitude(), d.getLongitude()
        );
    }

    /**
     * Calculates distance between two Deliveries.
     */
    private double dist(Delivery d1, Delivery d2) {
        return DistanceCalculator.calculateDistance(
                d1.getLatitude(), d1.getLongitude(),
                d2.getLatitude(), d2.getLongitude()
        );
    }

    private void mergeTours(Map<Delivery, SubTour> tourMap, SubTour tour1, SubTour tour2) {
        // add all deliveries from tour2 to tour1
        tour1.deliveries.addAll(tour2.deliveries);

        // also add the volume and weight
        tour1.currentWeight += tour2.currentWeight;
        tour1.currentVolume += tour2.currentVolume;

        // update the map to point all deliveries in tour2 to tour1
        for (Delivery del: tour2.deliveries) {
            tourMap.put(del, tour1);
        }

    }
}
