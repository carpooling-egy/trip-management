package com.example.demo.DAOs;

import com.example.demo.Models.EntityClasses.DriverOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public interface DriverOfferRepository extends JpaRepository<DriverOffer, String> {
// in DriverOfferRepository.java

    @Query("""
  SELECT d
    FROM DriverOffer d
   WHERE d.userId = :userId
     AND d.estimatedArrivalTime > :now
  """)
    List<DriverOffer> findUpcomingOffers(
            @Param("userId") String userId,
            @Param("now") ZonedDateTime now
    );

}
