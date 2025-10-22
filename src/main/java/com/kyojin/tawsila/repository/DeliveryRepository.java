package com.kyojin.tawsila.repository;

import com.kyojin.tawsila.entity.Delivery;
import com.kyojin.tawsila.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByStatus(DeliveryStatus status);
    List<Delivery> findAllByStatus(DeliveryStatus status);
}
