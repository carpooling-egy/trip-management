package com.example.demo.DTOs;

import com.example.demo.Models.EntityClasses.DriverOffer;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class DriverOfferWithRidersDTO {
    // Core offer fields
    private final String id;
    private final BigDecimal sourceLatitude;
    private final BigDecimal sourceLongitude;
    private final String sourceAddress;
    private final BigDecimal destinationLatitude;
    private final BigDecimal destinationLongitude;
    private final String destinationAddress;
    private final ZonedDateTime departureTime;
    private final ZonedDateTime maxEstimatedArrivalTime;
    private final int capacity;
    // ... any others you need

    // Matched riders (empty list if none)
    private final List<RiderMatchInfoDTO> matchedRiders;

    public DriverOfferWithRidersDTO(
            DriverOffer offer,
            List<RiderMatchInfoDTO> matchedRiders
    ) {
        this.id                      = offer.getId();
        this.sourceLatitude          = offer.getSourceLatitude();
        this.sourceLongitude         = offer.getSourceLongitude();
        this.sourceAddress           = offer.getSourceAddress();
        this.destinationLatitude     = offer.getDestinationLatitude();
        this.destinationLongitude    = offer.getDestinationLongitude();
        this.destinationAddress      = offer.getDestinationAddress();
        this.departureTime           = offer.getDepartureTime();
        this.maxEstimatedArrivalTime = offer.getMaxEstimatedArrivalTime();
        this.capacity                = offer.getCapacity();
        // map each RideMatch to a RiderMatchInfoDTO
        this.matchedRiders           = matchedRiders;
    }
}
