package com.example.demo.Services;

import com.example.demo.DTOs.UserProfileDTO;
import com.example.demo.Enums.GenderType;
import com.example.demo.Exceptions.ProfileFetchException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProfileService {

    private final RestTemplate rest;
    private final ObjectMapper mapper;
    private final String urlTemplate;

    public ProfileService(
            @Value("${profile.service.url-template}") String urlTemplate
    ) {
        this.rest        = new RestTemplate();
        this.mapper      = new ObjectMapper();
        this.urlTemplate = urlTemplate;
    }

    /**
     * Fetches fullName and gender for a given user ID from the profile service.
     */
    public UserProfileDTO getUserProfile(String userId) {
        try {
            // e.g. GET http://profiles-service/api/users/{userId}/profile
            ResponseEntity<String> response = rest.getForEntity(urlTemplate, String.class, userId);
            String json = response.getBody();
            JsonNode root = mapper.readTree(json);

            // Extract fullName
            String fullName;
            if (root.has("fullName")) {
                fullName = root.get("fullName").asText();
            } else {
                throw new ProfileFetchException(
                        "fullName field not found in profile response for user " + userId);
            }

            // Extract gender (either as raw text or nested field)
            String genderValue;
            if (root.has("gender")) {
                genderValue = root.get("gender").asText();
            } else if (root.isTextual()) {
                genderValue = root.textValue();
            } else {
                throw new ProfileFetchException(
                        "gender field not found in profile response for user " + userId);
            }

            GenderType gender = GenderType.valueOf(genderValue.toUpperCase());

            return new UserProfileDTO(userId, fullName, gender);

        } catch (RestClientException | JsonProcessingException | IllegalArgumentException e) {
            throw new ProfileFetchException("Failed to fetch profile for user " + userId, e);
        }
    }
}
