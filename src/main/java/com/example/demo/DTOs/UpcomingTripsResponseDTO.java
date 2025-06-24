package com.example.demo.DTOs;

import com.example.demo.DTOs.summarizedCards.DriverOfferCardDTO;
import com.example.demo.DTOs.summarizedCards.MatchedRiderRequestCardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpcomingTripsResponseDTO {

    private List<DriverOfferCardDTO> driverTrips;
    private List<MatchedRiderRequestCardDTO> riderTrips;
}
