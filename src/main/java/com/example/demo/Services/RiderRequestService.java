package com.example.demo.Services;

import com.example.demo.DTOs.MatchedRiderTripDTO;
import com.example.demo.DTOs.RiderRequestDTO;
import com.example.demo.DTOs.UserProfileDTO;
import com.example.demo.Enums.GenderType;
import com.example.demo.Models.EntityClasses.RiderRequest;
import com.example.demo.DAOs.RiderRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiderRequestService {

    private final RiderRequestRepository riderRepo;

    private final ProfileService profileService;

    public List<MatchedRiderTripDTO> getMatchedUpcomingTripsByRider(String userId) {
        var now = ZonedDateTime.now();
        List<RiderRequest> requests = riderRepo.findMatchedUpcoming(userId, now);

        return requests.stream()
                .map(req -> {
                    // Look up the driver profile:
                    String driverUserId = req.getRideMatch().getDriverOffer().getUserId();
                    UserProfileDTO driverProfile = profileService.getUserProfile(driverUserId);
                    // Build our enriched DTO:
                    return new MatchedRiderTripDTO(req, driverProfile);
                })
                .collect(Collectors.toList());
    }

    public List<RiderRequestDTO> getUnmatchedUpcomingTripsByRider(String userId) {
        return riderRepo
                .findUnmatchedUpcoming(userId, ZonedDateTime.now())
                .stream()
                .map(RiderRequestDTO::new)                       // use the DTO constructor
                .collect(Collectors.toList());                   // Java 8+ safe collect
    }
}
