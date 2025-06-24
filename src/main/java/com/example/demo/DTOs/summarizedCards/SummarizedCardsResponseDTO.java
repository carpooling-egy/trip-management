package com.example.demo.DTOs.summarizedCards;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SummarizedCardsResponseDTO {
    private List<DriverOfferCardDTO> driverOffers;
    private List<RiderRequestCardDTO> unmatchedRiderRequests;
    private List<MatchedRiderRequestCardDTO> matchedRiderRequests;
}
