package com.example.demo.Controllers;

import com.example.demo.DTOs.DriverOfferWithRidersDTO;
import com.example.demo.DTOs.MatchedRiderTripDTO;
import com.example.demo.DTOs.RiderRequestDTO;
import com.example.demo.DTOs.UpcomingTripsResponseDTO;
import com.example.demo.DTOs.cards.DriverOfferCardDTO;
import com.example.demo.DTOs.cards.MatchedRiderRequestCardDTO;
import com.example.demo.DTOs.cards.RiderRequestCardDTO;
import com.example.demo.DTOs.cards.SummarizedCardsResponseDTO;
import com.example.demo.Services.DriverOfferService;
import com.example.demo.Services.RiderRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class TripManagementController {

    @Autowired
    private DriverOfferService driverOfferService;

    @Autowired
    private RiderRequestService riderRequestService;

    /**
     * GET /api/users/{userId}/upcomingTrips
     * Returns all matched upcoming trips for the specified user, whether as driver or rider.
     */
    @GetMapping("/upcomingTrips/{userId}")
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
    @GetMapping("/myTrips/{userId}")
    public ResponseEntity<List<RiderRequestDTO>> getMyUpcomingUnmatchedTrips(@PathVariable String userId) {
        List<RiderRequestDTO> trips = riderRequestService.getUnmatchedUpcomingTripsByRider(userId);
        return ResponseEntity.ok(trips);
    }

    /**
     * GET /api/users/summarized-cards/{userId}
     * Returns all matched upcoming trips where the specified user is a rider.
     */
    @GetMapping("/summarized-cards/{userId}")
    public ResponseEntity<SummarizedCardsResponseDTO> getSummarizedCards(@PathVariable String userId) {
        // Trips where user is the driver
        List<DriverOfferCardDTO> driverOffers = driverOfferService.getAllUpcomingTrips(userId);
        // Trips where user is the rider
        List<MatchedRiderRequestCardDTO> matchedRiderRequests = riderRequestService.getMatchedUpcomingTripsByRider(userId);
        // Unmatched trips where user is a rider
        List<RiderRequestCardDTO> unmatchedRiderRequests = riderRequestService.getUnmatchedUpcomingTripsByRider(userId);

        SummarizedCardsResponseDTO response = new SummarizedCardsResponseDTO(
                driverOffers, unmatchedRiderRequests, matchedRiderRequests
        );
        return ResponseEntity.ok(response);
    }


}
