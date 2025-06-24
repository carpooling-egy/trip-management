// com.example.demo.DTOs.cards/MatchedRiderRequestDetailedCardDTO.java
package com.example.demo.DTOs.detailedCards;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.demo.Enums.GenderType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class MatchedRiderRequestDetailedCardDTO {
    private String id;
    private ZonedDateTime createdAt;

    private BigDecimal pickupLatitude;
    private BigDecimal pickupLongitude;
    private String pickupAddress;
    private BigDecimal dropoffLatitude;
    private BigDecimal dropoffLongitude;
    private String dropoffAddress;

    private ZonedDateTime pickupTime;
    private ZonedDateTime dropoffTime;

    private String driverFirstName;
    private String driverLastName;
    private GenderType driverGender;

    private boolean sameGender;
    private boolean matched;
}