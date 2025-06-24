package com.example.demo.DTOs.detailedCards;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class UnmatchedRiderRequestDetailedCardDTO {
    private String id;
    private BigDecimal sourceLatitude;
    private BigDecimal sourceLongitude;
    private String sourceAddress;
    private BigDecimal destinationLatitude;
    private BigDecimal destinationLongitude;
    private String destinationAddress;

    private boolean sameGender;
    private ZonedDateTime createdAt;

    private ZonedDateTime earliestDepartureTime;
    private ZonedDateTime latestArrivalTime;
    private int maxWalkingTimeMinutes;
    private int numberOfRiders;
    private boolean matched;
}