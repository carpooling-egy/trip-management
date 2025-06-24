package com.example.demo.DTOs;

import com.example.demo.Enums.GenderType;
import com.example.demo.Models.EntityClasses.RiderRequest;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
public class MatchedRiderTripDTO extends RiderRequestDTO {
    // pickup/drop‐off coords & addresses
    private BigDecimal pickupLatitude;
    private BigDecimal pickupLongitude;
    private String pickupAddress;
    private BigDecimal dropoffLatitude;
    private BigDecimal dropoffLongitude;
    private String dropoffAddress;

    // pickup/drop‐off times
    private ZonedDateTime pickupTime;
    private ZonedDateTime dropoffTime;

    // driver profile info
    private String driverId;
    private String driverFistName;
    private String driverLastName;
    private GenderType driverGender;

    public MatchedRiderTripDTO(
            RiderRequest request,
            UserProfileDTO driverProfile
    ) {
        // 1) initialize all the RiderRequestDTO fields:
        super(request);

        // 2) fill in the ride‐match details
        var match = request.getRideMatch();
        this.pickupTime      = match.getPickupPoint().getExpectedArrivalTime();
        this.pickupLatitude  = match.getPickupPoint().getLatitude();
        this.pickupLongitude = match.getPickupPoint().getLongitude();
        this.pickupAddress   = match.getPickupPoint().getAddress();

        this.dropoffTime      = match.getDropoffPoint().getExpectedArrivalTime();
        this.dropoffLatitude  = match.getDropoffPoint().getLatitude();
        this.dropoffLongitude = match.getDropoffPoint().getLongitude();
        this.dropoffAddress   = match.getDropoffPoint().getAddress();

        // 3) attach the driver’s user/profile info
        var driverOffer = match.getDriverOffer();
        this.driverId     = driverOffer.getUserId();
        this.driverFistName = driverProfile.getFirstName();
        this.driverLastName = driverProfile.getLastName();
        this.driverGender = driverProfile.getGender();
    }
}
