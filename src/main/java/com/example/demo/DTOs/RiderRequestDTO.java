package com.example.demo.DTOs;

import com.example.demo.Models.EntityClasses.RiderRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.ZonedDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderRequestDTO extends BaseTripDTO {
    @NonNull
    @NotBlank
    private ZonedDateTime earliestDepartureTime;
    @NonNull
    @NotBlank
    private ZonedDateTime latestArrivalTime;
    @NotBlank
    private int maxWalkingTimeMinutes;
    @NotBlank
    private int numberOfRiders;
    private boolean isMatched;



    public RiderRequestDTO(RiderRequest riderRequest) {
        super(riderRequest.getId(), riderRequest.getUserId(), riderRequest.getSourceLatitude(),
                riderRequest.getSourceLongitude(), riderRequest.getSourceAddress(),
                riderRequest.getDestinationLatitude(), riderRequest.getDestinationLongitude(),
                riderRequest.getDestinationAddress(), riderRequest.isSameGender(),
                riderRequest.getCreatedAt(), riderRequest.getUpdatedAt());
        this.earliestDepartureTime = riderRequest.getEarliestDepartureTime();
        this.latestArrivalTime = riderRequest.getLatestArrivalTime();
        this.maxWalkingTimeMinutes = riderRequest.getMaxWalkingDurationMinutes();
        this.numberOfRiders = riderRequest.getNumberOfRiders();
        this.isMatched = riderRequest.isMatched();
    }
}
