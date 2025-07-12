package com.example.demo.DTOs.detailedCards;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DriverOfferDetailedCardDTO {
    private String id;
    private String sourceAddress;
    private String destinationAddress;

    private ZonedDateTime departureTime;
    private ZonedDateTime maxEstimatedArrivalTime;
    private int detourTimeMinutes;
    private int capacity;
    private boolean sameGender;

    private BigDecimal sourceLatitude;
    private BigDecimal sourceLongitude;
    private BigDecimal destinationLatitude;
    private BigDecimal destinationLongitude;

    private ZonedDateTime createdAt;

    private List<RiderMatchPointDTO> matchedRiders;
    private List<PathPointDTO> path;

    @Data
    @AllArgsConstructor
    public static class RiderMatchPointDTO {
        private String riderFirstName;
        private String riderLastName;
        private String riderGender;
        private ZonedDateTime pickupTime;
        private ZonedDateTime dropoffTime;
        private BigDecimal pickupLatitude;
        private BigDecimal pickupLongitude;
        private String pickupAddress;
        private BigDecimal dropoffLatitude;
        private BigDecimal dropoffLongitude;
        private String dropoffAddress;
    }

    @Data
    @AllArgsConstructor
    public static class PathPointDTO {
        private String type;
        private String reqId;
        private BigDecimal latitude;
        private BigDecimal longitude;
    }
}