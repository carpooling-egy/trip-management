package com.example.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingTripsResponseDTO {
    private List<DriverOfferWithRidersDTO> driverOffers;
    private List<MatchedRiderTripDTO> riderRequests;
}
