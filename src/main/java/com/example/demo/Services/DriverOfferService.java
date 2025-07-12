package com.example.demo.Services;

import com.example.demo.DAOs.DriverOfferRepository;
import com.example.demo.DTOs.detailedCards.DriverOfferDetailedCardDTO;
import com.example.demo.DTOs.summarizedCards.DriverOfferCardDTO;
import com.example.demo.Models.EntityClasses.DriverOffer;
import com.example.demo.Models.EntityClasses.PathPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverOfferService {

    private final DriverOfferRepository driverRepo;
    private final ProfileService profileService;

    /**
     * Fetch upcoming driver offers and map to card DTOs.
     */
    public List<DriverOfferCardDTO> getSummarizedAllUpcomingTrips(String driverId) {
        List<DriverOffer> offers = driverRepo.findUpcomingOffers(driverId, ZonedDateTime.now());

        return offers.stream()
                .map(offer -> {
                    int capacity = offer.getCapacity();
                    int remainingCapacity = capacity - offer.getCurrentNumberOfRequests();

                    return new DriverOfferCardDTO(
                            offer.getId(),
                            "driver-offer",
                            offer.getSourceAddress(),
                            offer.getDestinationAddress(),
                            offer.getCreatedAt(),
                            capacity,
                            remainingCapacity
                    );
                })
                .collect(Collectors.toList());
    }


    public DriverOfferDetailedCardDTO getDriverOfferDetail(
            String userId,
            String offerId
    ) {
        DriverOffer offer = driverRepo.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("DriverOffer not found"));
        if (!offer.getUserId().equals(userId)) {
            throw new IllegalArgumentException("DriverOffer does not belong to the user");
        }

        // map matched riders
        List<DriverOfferDetailedCardDTO.RiderMatchPointDTO> riders = offer.getRideMatches().stream()
                .map(match -> {
                    var riderReq = match.getRiderRequest();
                    var profile = profileService.getUserProfile(riderReq.getUserId());
                    return new DriverOfferDetailedCardDTO.RiderMatchPointDTO(
                            profile.getFirstName(),
                            profile.getLastName(),
                            profile.getGender().name(),
                            match.getPickupPoint().getExpectedArrivalTime(),
                            match.getDropoffPoint().getExpectedArrivalTime(),
                            match.getPickupPoint().getLatitude(),
                            match.getPickupPoint().getLongitude(),
                            match.getPickupPoint().getAddress(),
                            match.getDropoffPoint().getLatitude(),
                            match.getDropoffPoint().getLongitude(),
                            match.getDropoffPoint().getAddress()
                    );
                })
                .collect(Collectors.toList());

        // map path (sorted by pathOrder)
        List<DriverOfferDetailedCardDTO.PathPointDTO> path = offer.getPathPoints().stream()
                .sorted(Comparator.comparingInt(PathPoint::getPathOrder))
                .map(p -> new DriverOfferDetailedCardDTO.PathPointDTO(
                        p.getType().name().toLowerCase(),
                        p.getRiderRequest().getId(),
                        p.getLatitude(),
                        p.getLongitude()
                ))
                .collect(Collectors.toList());

        return new DriverOfferDetailedCardDTO(
                offer.getId(),
                offer.getSourceAddress(),
                offer.getDestinationAddress(),
                offer.getDepartureTime(),
                offer.getMaxEstimatedArrivalTime(),
                offer.getDetourDurationMinutes(),
                offer.getCapacity(),
                offer.isSameGender(),
                offer.getSourceLatitude(),
                offer.getSourceLongitude(),
                offer.getDestinationLatitude(),
                offer.getDestinationLongitude(),
                offer.getCreatedAt(),
                riders,
                path
        );
    }
}
