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
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Double weightKg;

    @NotNull
    private Double volumeM3;

    private String timeSlot; // ex: "09:00-12:00"

    @Enumerated(EnumType.STRING)
    @NotNull
    private DeliveryStatus status;

    @PrePersist
    protected void onCreate() {
        if (status == null) status = DeliveryStatus.PENDING;
    }
}
