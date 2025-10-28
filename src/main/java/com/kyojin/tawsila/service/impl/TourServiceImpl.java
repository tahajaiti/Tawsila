package com.kyojin.tawsila.service.impl;

import com.kyojin.tawsila.dto.DeliveryDTO;
import com.kyojin.tawsila.dto.TourDTO;
import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.entity.Tour;
import com.kyojin.tawsila.entity.Warehouse;
import com.kyojin.tawsila.enums.AlgorithmType;
import com.kyojin.tawsila.exception.BadRequestException;
import com.kyojin.tawsila.exception.NotFoundException;
import com.kyojin.tawsila.mapper.TourMapper;
import com.kyojin.tawsila.optimizer.TourOptimizer;
import com.kyojin.tawsila.repository.DeliveryRepository;
import com.kyojin.tawsila.repository.TourRepository;
import com.kyojin.tawsila.repository.VehicleRepository;
import com.kyojin.tawsila.service.TourService;
import com.kyojin.tawsila.util.DistanceCalculator;
import com.kyojin.tawsila.util.ParseUtil;
import com.kyojin.tawsila.util.TourValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;
    private final VehicleRepository vehicleRepository;
    private final DeliveryRepository deliveryRepository;
    private final Warehouse warehouse;
    private final TourOptimizer nearestNeighborOptimizer;
    private final TourOptimizer clarkeWrightOptimizer;

    @Override
    @Transactional
    public TourDTO createTour(TourDTO dto) {
        Tour tour = tourMapper.toEntity(dto);

        if (dto.getVehicle() != null && dto.getVehicle().getId() != null) {
            var vehicle = vehicleRepository.findById(dto.getVehicle().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID"));
            tour.setVehicle(vehicle);
        }

        if (dto.getDeliveries() != null && !dto.getDeliveries().isEmpty()) {
            List<Delivery> deliveries = findAndLinkDeliveries(dto.getDeliveries(), tour);
            tour.setDeliveries(deliveries);
        }

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
    @Transactional
    public TourDTO updateTour(Long id, TourDTO dto) {
        var tour = tourRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour not found with id: " + id));

        tourMapper.updateEntityFromDTO(dto, tour);

        TourValidator.validateCapactity(tour);

        var updatedTour = tourRepository.save(tour);
        return tourMapper.toDTO(updatedTour);
    }

    @Override
    @Transactional
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


    private List<Delivery> findAndLinkDeliveries(List<DeliveryDTO> deliveryDTOs, Tour tour) {
        Set<Long> deliveryIds = deliveryDTOs.stream()
                .map(DeliveryDTO::getId)
                .collect(Collectors.toSet());

        List<Delivery> deliveries = deliveryRepository.findAllById(deliveryIds);

        if (deliveries.size() != deliveryIds.size()) {
            Set<Long> foundIds = deliveries.stream()
                    .map(Delivery::getId)
                    .collect(Collectors.toSet());
            Long missingId = deliveryIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .findFirst()
                    .orElse(null);
            throw new NotFoundException("Invalid delivery ID: " + missingId);
        }

        for (Delivery delivery : deliveries) {
            if (delivery.getTour() != null) {
                throw new BadRequestException("Delivery " + delivery.getId()
                        + " is already assigned to tour " + delivery.getTour().getId());
            }
            delivery.setTour(tour);
        }

        return deliveries;

    }
}
