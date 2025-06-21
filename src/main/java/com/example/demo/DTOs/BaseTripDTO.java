package com.example.demo.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTripDTO {

    private String id;
    @NotBlank
    private String userId;
    @NonNull
    private BigDecimal sourceLatitude;
    @NonNull
    private BigDecimal sourceLongitude;
    @NotBlank
    private String sourceAddress;
    @NonNull
    private BigDecimal destinationLatitude;
    @NonNull
    private BigDecimal destinationLongitude;
    @NotBlank
    private String destinationAddress;

    // common preferences
    private boolean sameGender = false;

    // audit
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}