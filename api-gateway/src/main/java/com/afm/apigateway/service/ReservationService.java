package com.afm.apigateway.service;

import model.flights.Flight;
import model.prenotation.Reservation;
import model.utils.RequestFlight;
import model.utils.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    public RestTemplate restTemplate;

    @Value("${services.flights.resAddress}")
    private String resAddress;

    public List<Reservation> createReservationsGet(@RequestBody List<ReservationRequest> request) {
        HttpEntity<List<ReservationRequest>> resRequest = new HttpEntity<>(request);

        List<Reservation> resList = restTemplate.exchange(
                resAddress + "/reservation/creates",
                HttpMethod.POST,
                resRequest,
                new ParameterizedTypeReference<List<Reservation>>() {
                }
        ).getBody();

        return resList;
    }
}
