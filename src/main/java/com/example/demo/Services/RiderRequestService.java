package com.example.demo.Services;

import com.example.demo.DTOs.detailedCards.MatchedRiderRequestDetailedCardDTO;
import com.example.demo.DTOs.detailedCards.UnmatchedRiderRequestDetailedCardDTO;
import com.example.demo.DTOs.summarizedCards.MatchedRiderRequestCardDTO;
import com.example.demo.DTOs.summarizedCards.RiderRequestCardDTO;
import com.example.demo.Models.EntityClasses.RiderRequest;
import com.example.demo.DAOs.RiderRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiderRequestService {

    private final RiderRequestRepository riderRepo;

    private final ProfileService profileService;

    public List<MatchedRiderRequestCardDTO> getSummarizedMatchedUpcomingTripsByRider(String userId) {
        ZonedDateTime now = ZonedDateTime.now();
        List<RiderRequest> requests = riderRepo.findMatchedUpcoming(userId, now);

        return requests.stream()
                .map(req -> {
                    // pull the ride‐match and driver userId
                    var match = req.getRideMatch();
                    var driverId = match.getDriverOffer().getUserId();
                    // look up driver’s profile
                    var profile = profileService.getUserProfile(driverId);

                    return new MatchedRiderRequestCardDTO(
                            req.getId(),
                            "rider-request",              // type field
                            req.getSourceAddress(),
                            req.getDestinationAddress(),
                            req.getCreatedAt(),
                            true,                         // matched flag
                            profile.getFirstName(),
                            profile.getLastName()
                    );
                })
                .collect(Collectors.toList());
    }


    public List<RiderRequestCardDTO> getSummarizedUnmatchedUpcomingTripsByRider(String userId) {
        ZonedDateTime now = ZonedDateTime.now();
        List<RiderRequest> requests = riderRepo.findUnmatchedUpcoming(userId, now);

        return requests.stream()
                .map(req -> new RiderRequestCardDTO(
                        req.getId(),
                        "rider-request",              // type field
                        req.getSourceAddress(),
                        req.getDestinationAddress(),
                        req.getCreatedAt(),
                        false   // unmatched
                ))
                .collect(Collectors.toList());
    }

    public boolean isRequestMatched(String requestId) {
        return riderRepo.isRequestMatched(requestId);
    }

    public MatchedRiderRequestDetailedCardDTO getMatchedRiderRequestDetail(
            String userId,
            String requestId
    ) {
        RiderRequest req = riderRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("RiderRequest not found"));

        if (!req.getUserId().equals(userId)) {
            throw new IllegalArgumentException("RiderRequest does not belong to the user");
        }

        var match = req.getRideMatch();
        var driverID = match.getDriverOffer().getUserId();
        var driverProfile = profileService.getUserProfile(driverID);

        return new MatchedRiderRequestDetailedCardDTO(
                req.getId(),
                req.getCreatedAt(),

                match.getPickupPoint().getLatitude(),
                match.getPickupPoint().getLongitude(),
                match.getPickupPoint().getAddress(),
                match.getDropoffPoint().getLatitude(),
                match.getDropoffPoint().getLongitude(),
                match.getDropoffPoint().getAddress(),

                match.getPickupPoint().getExpectedArrivalTime(),
                match.getDropoffPoint().getExpectedArrivalTime(),

                driverProfile.getFirstName(),
                driverProfile.getLastName(),
                driverProfile.getGender(),

                req.isSameGender(),
                true
        );
    }

    public UnmatchedRiderRequestDetailedCardDTO getUnmatchedRiderRequestDetail(
            String userId,
            String requestId
    ) {
        RiderRequest req = riderRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("RiderRequest not found"));

        if (!req.getUserId().equals(userId)) {
            throw new IllegalArgumentException("RiderRequest does not belong to the user");
        }
        return new UnmatchedRiderRequestDetailedCardDTO(
                req.getId(),
                req.getSourceLatitude(),
                req.getSourceLongitude(),
                req.getSourceAddress(),
                req.getDestinationLatitude(),
                req.getDestinationLongitude(),
                req.getDestinationAddress(),

                req.isSameGender(),
                req.getCreatedAt(),

                req.getEarliestDepartureTime(),
                req.getLatestArrivalTime(),
                req.getMaxWalkingDurationMinutes(),
                req.getNumberOfRiders(),
                false
        );
    }
}
