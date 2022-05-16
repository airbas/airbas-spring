package com.afm.apigateway.service;

import model.auth.UserBas;
import model.flights.AirPlane;
import model.flights.Flight;
import model.prenotation.Reservation;
import model.utils.LoginRequest;
import model.utils.RequestAddFlight;
import model.utils.RequestFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FlightsService {

    @Autowired
    public RestTemplate restTemplate;

    @Value("${services.flights.flightsAddress}")
    private String flightAddress;

    public List<Flight> oneWayRest(@RequestBody RequestFlight request) {
        HttpEntity<RequestFlight> flightsRequest = new HttpEntity<>(request);


        List<Flight> flightsList = restTemplate.exchange(
                flightAddress + "/flights/justGone",
                HttpMethod.POST,
                flightsRequest,
                new ParameterizedTypeReference<List<Flight>>() {
                }
        ).getBody();

        return flightsList;
    }

    public List<List<Flight>> fullTripRest(@RequestBody RequestFlight request) {
        HttpEntity<RequestFlight> flightsRequest = new HttpEntity<>(request);


        List<List<Flight>> flightsList = restTemplate.exchange(
                flightAddress + "/flights/fullTrip",
                HttpMethod.POST,
                flightsRequest,
                new ParameterizedTypeReference<List<List<Flight>>>() {
                }
        ).getBody();

        return flightsList;
    }

    // Not used (for now)
    public AirPlane getAirplaneRest(@RequestBody String name) {

        AirPlane airplane = restTemplate.getForObject(
                flightAddress + "/flights/getAirplane?name=" + name ,
                AirPlane.class
        );

        return airplane;
    }

    public AirPlane bookSeatRest(String airPlaneName, String seatCord) {

        AirPlane airplane = restTemplate.getForObject(
                flightAddress + "/flights/bookSeat?name=" + airPlaneName + "&seatCord=" + seatCord ,
                AirPlane.class
        );

        return airplane;
    }

    public List<AirPlane> bookSeatRestMult(List<Reservation> reservations) {
        HttpEntity<List<Reservation>> flightsRequest = new HttpEntity<>(reservations);


        List<AirPlane> airplaneList = restTemplate.exchange(
                flightAddress + "/flights/bookSeatMulti",
                HttpMethod.POST,
                flightsRequest,
                new ParameterizedTypeReference<List<AirPlane>>() {
                }
        ).getBody();

        return airplaneList;
    }

    public AirPlane removeBookSeatRest(String airPlaneName, String seatCord) {

        AirPlane airplane = restTemplate.getForObject(
                flightAddress + "/flights/removeBookSeat?name=" + airPlaneName + "&seatCord=" + seatCord ,
                AirPlane.class
        );

        return airplane;
    }

    public List<Flight> addFlights(List<RequestAddFlight> request) {
        HttpEntity<List<RequestAddFlight>> flightsRequest = new HttpEntity<>(request);

        List<Flight> flightsList = restTemplate.exchange(
                flightAddress + "/flights/add",
                HttpMethod.POST,
                flightsRequest,
                new ParameterizedTypeReference<List<Flight>>() {
                }
        ).getBody();

        return flightsList;
    }


}
