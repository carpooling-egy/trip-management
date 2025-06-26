package com.example.demo.Controllers;

import com.example.demo.DTOs.UpcomingTripsResponseDTO;
import com.example.demo.DTOs.detailedCards.DriverOfferDetailedCardDTO;
import com.example.demo.DTOs.detailedCards.MatchedRiderRequestDetailedCardDTO;
import com.example.demo.DTOs.detailedCards.UnmatchedRiderRequestDetailedCardDTO;
import com.example.demo.DTOs.summarizedCards.DriverOfferCardDTO;
import com.example.demo.DTOs.summarizedCards.MatchedRiderRequestCardDTO;
import com.example.demo.DTOs.summarizedCards.UnmatchedRiderRequestCardDTO;
import com.example.demo.DTOs.summarizedCards.SummarizedCardsResponseDTO;
import com.example.demo.Services.DriverOfferService;
import com.example.demo.Services.RiderRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trip-management")
public class TripManagementController {

    @Autowired
    private DriverOfferService driverOfferService;

    @Autowired
    private RiderRequestService riderRequestService;

    /**
     * Returns all matched upcoming trips for the specified user, whether as driver or rider.
     */
    @GetMapping("/upcomingTrips/{userId}")
    public ResponseEntity<UpcomingTripsResponseDTO> getUpcomingTrips(@PathVariable String userId) {
        // Trips where user is the driver
        List<DriverOfferCardDTO> driverTrips = driverOfferService.getSummarizedAllUpcomingTrips(userId);
        // Trips where user is the rider
        List<MatchedRiderRequestCardDTO> riderTrips = riderRequestService.getSummarizedMatchedUpcomingTripsByRider(userId);

        UpcomingTripsResponseDTO response = new UpcomingTripsResponseDTO(driverTrips, riderTrips);
        return ResponseEntity.ok(response);
    }

    /**
      * Returns all upcoming unmatched trips where the specified user is a rider.
     */
    @GetMapping("/pending-rider-requests/{userId}")
    public ResponseEntity<List<UnmatchedRiderRequestCardDTO>> getMyUpcomingUnmatchedTrips(@PathVariable String userId) {
        List<UnmatchedRiderRequestCardDTO> unmatchedRiderRequests = riderRequestService.getSummarizedUnmatchedUpcomingTripsByRider(userId);
        return ResponseEntity.ok(unmatchedRiderRequests);
    }

    /**
     * GET /trip-management/summarized-cards/{userId}
     * Returns all matched upcoming trips where the specified user is a rider.
     */
    @GetMapping("/summarized-cards/{userId}")
    public ResponseEntity<SummarizedCardsResponseDTO> getSummarizedCards(@PathVariable String userId) {
        // Trips where user is the driver
        List<DriverOfferCardDTO> driverOffers = driverOfferService.getSummarizedAllUpcomingTrips(userId);
        // Trips where user is the rider
        List<MatchedRiderRequestCardDTO> matchedRiderRequests = riderRequestService.getSummarizedMatchedUpcomingTripsByRider(userId);
        // Unmatched trips where user is a rider
        List<UnmatchedRiderRequestCardDTO> unmatchedRiderRequests = riderRequestService.getSummarizedUnmatchedUpcomingTripsByRider(userId);

        SummarizedCardsResponseDTO response = new SummarizedCardsResponseDTO(
                driverOffers, unmatchedRiderRequests, matchedRiderRequests
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detailed-card/{cardType}")
    public ResponseEntity<?> getDetailedCard(
            @PathVariable String cardType,
            @RequestBody Map<String, String> body
    ) {
        String userId = body.get("userId");
        String cardId = body.get("cardId");

        switch (cardType.toLowerCase()) {
            case "rider-request":
                boolean matched = riderRequestService.isRequestMatched(cardId);
                if (matched) {
                    MatchedRiderRequestDetailedCardDTO dto =
                            riderRequestService.getMatchedRiderRequestDetail(userId, cardId);
                    return ResponseEntity.ok(dto);
                } else {
                    UnmatchedRiderRequestDetailedCardDTO dto =
                            riderRequestService.getUnmatchedRiderRequestDetail(userId, cardId);
                    return ResponseEntity.ok(dto);
                }

            case "driver-offer":
                DriverOfferDetailedCardDTO dto =
                        driverOfferService.getDriverOfferDetail(userId, cardId);
                return ResponseEntity.ok(dto);

            default:
                return ResponseEntity.badRequest()
                        .body("Invalid cardType: " + cardType);
        }
    }
}
