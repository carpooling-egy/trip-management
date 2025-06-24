package com.example.demo.DTOs.cards;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class RiderRequestCardDTO {
    private String id;
    private String type = "rider-request";
    private String sourceAddress;
    private String destinationAddress;
    private ZonedDateTime createdAt;
    private boolean matched;
}
