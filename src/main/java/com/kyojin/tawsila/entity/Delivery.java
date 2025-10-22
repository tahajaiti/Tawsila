package com.kyojin.tawsila.entity;

import com.kyojin.tawsila.enums.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double latitude;

    @NotNull
    @Column(nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "weight_kg", nullable = false)
    private Double weightKg;

    @NotNull
    @Column(name = "volume_m3", nullable = false)
    private Double volumeM3;

    @Column(name = "time_slot")
    private String timeSlot; // ex: "09:00-12:00"

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @PrePersist
    protected void onCreate() {
        if (status == null) status = DeliveryStatus.PENDING;
    }
}
