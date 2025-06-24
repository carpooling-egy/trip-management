package com.example.demo.DTOs.summarizedCards;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class MatchedRiderRequestCardDTO {
    private String id;
    private String type = "rider-request";
    private String sourceAddress;
    private String destinationAddress;
    private ZonedDateTime createdAt;
    private boolean matched = true;
    private String driverFirstName;
    private String driverLastName;
}
