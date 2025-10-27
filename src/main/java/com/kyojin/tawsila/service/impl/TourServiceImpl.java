package com.kyojin.tawsila.service.impl;

import com.kyojin.tawsila.dto.TourDTO;
import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Tour;
import com.kyojin.tawsila.entity.Warehouse;
import com.kyojin.tawsila.enums.AlgorithmType;
import com.kyojin.tawsila.exception.NotFoundException;
import com.kyojin.tawsila.mapper.TourMapper;
import com.kyojin.tawsila.optimizer.TourOptimizer;
import com.kyojin.tawsila.repository.TourRepository;
import com.kyojin.tawsila.service.TourService;
import com.kyojin.tawsila.util.DistanceCalculator;
import com.kyojin.tawsila.util.ParseUtil;
import com.kyojin.tawsila.util.TourValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;
    private final Warehouse warehouse;
    private final TourOptimizer nearestNeighborOptimizer;
    private final TourOptimizer clarkeWrightOptimizer;

    @Override
    public TourDTO createTour(TourDTO dto) {
        Tour tour = tourMapper.toEntity(dto);

        TourValidator.validateCapactity(tour);

        var savedTour = tourRepository.save(tour);

        return tourMapper.toDTO(savedTour);
    }

    @Override
    public Optional<TourDTO> getTourById(Long id) {
        return tourRepository.findById(id)
                .map(tourMapper::toDTO);
    }

    @Override
    public List<TourDTO> getAllTours() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toDTO)
                .toList();
    }

    @Override
    public TourDTO updateTour(Long id, TourDTO dto) {
        var tour = tourRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour not found with id: " + id));

        tourMapper.updateEntityFromDTO(dto, tour);

        TourValidator.validateCapactity(tour);

        var updatedTour = tourRepository.save(tour);
        return tourMapper.toDTO(updatedTour);
    }

    @Override
    public void deleteTour(Long id) {
        tourRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour not found with id: " + id));

        tourRepository.deleteById(id);
    }

    @Override
    public TourDTO getOptimizedTour(Long tourId, String algorithm) {
        AlgorithmType type = ParseUtil.parseType(algorithm, AlgorithmType.class);

        var tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Tour not found with id: " + tourId));

        var deliveries = tour.getDeliveries();
        var vehicle = tour.getVehicle();

        if (deliveries == null || deliveries.isEmpty()) {
            return tourMapper.toDTO(tour);
        }

        TourOptimizer optimizer = switch (type) {
            case NEAREST_NEIGHBOR -> nearestNeighborOptimizer;
            case CLARKE_WRIGHT -> clarkeWrightOptimizer;
        };

        var optimizedDeliveries = optimizer.calculateOptimalTour(warehouse, deliveries, vehicle);

        tour.setDeliveries(optimizedDeliveries);
        var optimizedTour = tourRepository.save(tour);

        return tourMapper.toDTO(optimizedTour);
    }


    @Override
    public double getTotalDistance(Long tourId) {
        var tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Tour not found with id: " + tourId));

        var deliveries = tour.getDeliveries();

        if (deliveries == null || deliveries.isEmpty()) {
            return 0.0;
        }

        // sorting deliveries by id to have a consistent order
        deliveries.sort(Comparator.comparing(Delivery::getId));

        // sum of the distances
        double totalDistance = 0.0;

        // storing the previous distances
        double prevLat = warehouse.getLatitude();
        double prevLon = warehouse.getLongitude();


        // calculate distance from warehouse to first delivery
        for (var delivery : deliveries) {
            totalDistance += DistanceCalculator.calculateDistance(
                    warehouse.getLatitude(),
                    warehouse.getLongitude(),
                    delivery.getLatitude(),
                    delivery.getLongitude()
            );

            // update previous location to current delivery
            prevLat = delivery.getLatitude();
            prevLon = delivery.getLongitude();
        }


        // marking the final distance to the warehouse
        totalDistance += DistanceCalculator.calculateDistance(
                prevLat,
                prevLon,
                warehouse.getLatitude(),
                warehouse.getLongitude()
        );

        return totalDistance;
    }


}
