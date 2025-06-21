package com.example.demo.DTOs;

import com.example.demo.Enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class RiderMatchInfoDTO {
    // Rider & profile
    private final String riderId;
    private final String riderName;
    private final GenderType riderGender;

    // Timing
    private final ZonedDateTime pickupTime;
    private final ZonedDateTime dropoffTime;

    // Pickup location
    private final BigDecimal pickupLatitude;
    private final BigDecimal pickupLongitude;
    private final String pickupAddress;

    // Drop‑off location
    private final BigDecimal dropoffLatitude;
    private final BigDecimal dropoffLongitude;
    private final String dropoffAddress;

}
