package com.kyojin.tawsila.service.impl;

import com.kyojin.tawsila.dto.TourDTO;
import com.kyojin.tawsila.entity.Tour;
import com.kyojin.tawsila.mapper.TourMapper;
import com.kyojin.tawsila.repository.TourRepository;
import com.kyojin.tawsila.service.TourService;

import java.util.List;
import java.util.Optional;

public class TourServiceImpl implements TourService {

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;

    public TourServiceImpl(TourMapper tourMapper, TourRepository tourRepository) {
        this.tourMapper = tourMapper;
        this.tourRepository = tourRepository;
    }

    @Override
    public TourDTO createTour(TourDTO dto) {
        Tour tour = tourMapper.toEntity(dto);

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
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));

        tourMapper.updateEntityFromDTO(dto, tour);

        var updatedTour = tourRepository.save(tour);
        return tourMapper.toDTO(updatedTour);
    }

    @Override
    public void deleteTour(Long id) {
        tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));

        tourRepository.deleteById(id);
    }


}
