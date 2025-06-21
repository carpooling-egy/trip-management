package com.example.demo.Controllers;

import com.example.demo.DTOs.DriverOfferWithRidersDTO;
import com.example.demo.DTOs.MatchedRiderTripDTO;
import com.example.demo.DTOs.RiderRequestDTO;
import com.example.demo.DTOs.UpcomingTripsResponseDTO;
import com.example.demo.Services.DriverOfferService;
import com.example.demo.Services.RiderRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}")
public class TripManagementController {

    @Autowired
    private DriverOfferService driverOfferService;

    @Autowired
    private RiderRequestService riderRequestService;

    /**
     * GET /api/users/{userId}/upcomingTrips
     * Returns all matched upcoming trips for the specified user, whether as driver or rider.
     */
    @GetMapping("/upcomingTrips")
    public ResponseEntity<UpcomingTripsResponseDTO> getUpcomingTrips(@PathVariable String userId) {
        // Trips where user is the driver
        List<DriverOfferWithRidersDTO> driverTrips = driverOfferService.getAllUpcomingTrips(userId);
        // Trips where user is the rider
        List<MatchedRiderTripDTO> riderTrips = riderRequestService.getMatchedUpcomingTripsByRider(userId);

        UpcomingTripsResponseDTO response = new UpcomingTripsResponseDTO(driverTrips, riderTrips);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/users/{userId}/myTrips
     * Returns all upcoming unmatched trips where the specified user is a rider.
     */
    @GetMapping("/myTrips")
    public ResponseEntity<List<RiderRequestDTO>> getMyUpcomingUnmatchedTrips(@PathVariable String userId) {
        List<RiderRequestDTO> trips = riderRequestService.getUnmatchedUpcomingTripsByRider(userId);
        return ResponseEntity.ok(trips);
    }
}
