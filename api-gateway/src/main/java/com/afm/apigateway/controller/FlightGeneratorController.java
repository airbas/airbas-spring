package com.afm.apigateway.controller;


import com.afm.apigateway.service.FlightsService;
import lombok.RequiredArgsConstructor;
import model.flights.AirPlane;
import model.flights.Flight;
import model.utils.RequestAddFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/")
@RestController
public class FlightGeneratorController {
    public final RestTemplate restTemplate;
    private final FlightsService flightService;

    @Value("${services.flights.flightsGenAddress}")
    private String flightAddress;

    @GetMapping("add")
    public List<Flight> addFlight(){
        System.out.println(flightAddress + "/generate");

        List<RequestAddFlight> flights = restTemplate.exchange(
                flightAddress + "/generate",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RequestAddFlight>>() {}
        ).getBody();

        List<Flight> flightAdded = flightService.addFlights(flights);

        return flightAdded;
    }
}
