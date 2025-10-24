package com.kyojin.tawsila.repository;

import com.kyojin.tawsila.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}
