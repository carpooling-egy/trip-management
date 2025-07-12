package com.example.demo.DTOs.summarizedCards;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class DriverOfferCardDTO {
    private String id;
    private String type = "driver-offer";
    private String sourceAddress;
    private String destinationAddress;
    private ZonedDateTime createdAt;
    private int capacity;
    private int remainingCapacity;
}
