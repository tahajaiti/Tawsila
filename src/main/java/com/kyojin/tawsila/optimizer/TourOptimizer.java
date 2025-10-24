package com.kyojin.tawsila.optimizer;

import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Warehouse;

import java.util.List;

public interface TourOptimizer {
    List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries);
}
