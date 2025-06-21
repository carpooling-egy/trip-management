package com.example.demo.Services;

import com.example.demo.DAOs.DriverOfferRepository;
import com.example.demo.DTOs.DriverOfferWithRidersDTO;
import com.example.demo.DTOs.RiderMatchInfoDTO;
import com.example.demo.Models.EntityClasses.DriverOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverOfferService {

    private final DriverOfferRepository driverRepo;
    private final ProfileService      profileService;

    public List<DriverOfferWithRidersDTO> getAllUpcomingTrips(String driverId) {
        List<DriverOffer> offers = driverRepo.findUpcomingOffers(driverId, ZonedDateTime.now());

        return offers.stream().map(offer -> {
            // build the list of RiderMatchInfoDTO
            List<RiderMatchInfoDTO> riders = offer.getRideMatches().stream()
                    .map(match -> {
                        var riderReq    = match.getRiderRequest();
                        var profile     = profileService.getUserProfile(riderReq.getUserId());
                        var pickupPoint = match.getPickupPoint();
                        var dropoffPoint= match.getDropoffPoint();

                        return new RiderMatchInfoDTO(
                                riderReq.getUserId(),
                                profile.getFullName(),
                                profile.getGender(),
                                pickupPoint.getExpectedArrivalTime(),
                                dropoffPoint.getExpectedArrivalTime(),
                                pickupPoint.getLatitude(),
                                pickupPoint.getLongitude(),
                                pickupPoint.getAddress(),
                                dropoffPoint.getLatitude(),
                                dropoffPoint.getLongitude(),
                                dropoffPoint.getAddress()
                        );
                    })
                    .collect(Collectors.toList());

            // pass that list into the DTO
            return new DriverOfferWithRidersDTO(offer, riders);
        }).collect(Collectors.toList());
    }
}

