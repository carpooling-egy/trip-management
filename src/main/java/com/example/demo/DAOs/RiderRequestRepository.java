package com.example.demo.DAOs;

import com.example.demo.Models.EntityClasses.RiderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public interface RiderRequestRepository extends JpaRepository<RiderRequest, String> {

    @Query("SELECT r FROM RiderRequest r WHERE r.userId = :userId AND r.isMatched = false AND r.latestArrivalTime > :now")
    List<RiderRequest> findUnmatchedUpcoming(@Param("userId") String userId, @Param("now") ZonedDateTime now);


    @Query("""
      SELECT r
        FROM RiderRequest r
       WHERE r.userId        = :userId
         AND r.isMatched     = true
         AND r.latestArrivalTime > :now
    """)
    List<RiderRequest> findMatchedUpcoming(
            @Param("userId") String userId,
            @Param("now") ZonedDateTime now
    );


    @Query("""
      SELECT r.isMatched
        FROM RiderRequest r
       WHERE r.id = :requestId
    """)
    boolean isRequestMatched(String requestId);

}
