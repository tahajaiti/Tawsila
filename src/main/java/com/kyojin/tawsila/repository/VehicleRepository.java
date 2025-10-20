package com.kyojin.tawsila.repository;

import com.kyojin.tawsila.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
