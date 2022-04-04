package com.afm.apigateway.controller;

import com.afm.apigateway.saga.orchestrators.ReservationCreationOrchestrator;
import com.afm.apigateway.security.jwt.JwtService;
import com.afm.apigateway.service.FlightsService;
import com.afm.apigateway.service.ReservationService;
import model.prenotation.Reservation;
import model.utils.ReservationRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("api/")
@RestController
public class ReservationController {
    private final FlightsService flightsService;
    private final ReservationService reservationService;
    private final JwtService jwtService;
    private ReservationCreationOrchestrator reservationCreationOrchestrator;

    public ReservationController(FlightsService flightsService,
                                 ReservationService reservationService, JwtService jwtService) {
        this.flightsService = flightsService;
        this.reservationService = reservationService;
        this.jwtService = jwtService;
        reservationCreationOrchestrator = new ReservationCreationOrchestrator(flightsService, reservationService);

    }

    @CrossOrigin
    @PostMapping("res/creates")
    public ResponseEntity<?> createReservation(@RequestBody List<ReservationRequest> requests) throws Throwable {
        List<Reservation> resCreated = reservationCreationOrchestrator.createReservation(requests);
        return new ResponseEntity(resCreated, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("res/get/{email}")
    public ResponseEntity<?> getReservation(@PathVariable String email, @RequestHeader HttpHeaders headers) {
        String jwt = headers.get("authorization").get(0);
        if(jwtService.validateToken(jwt, email)){
            List<Reservation> userReservations = reservationService.getReservation(email);
            return new ResponseEntity(userReservations, HttpStatus.CREATED);
        } else{
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @GetMapping("res/delete/{email}/{cod}")
    public ResponseEntity<?> deleteReservation(@PathVariable String email, @PathVariable String cod, @RequestHeader HttpHeaders headers) {
        String jwt = headers.get("authorization").get(0);
        if(jwtService.validateToken(jwt, email)){
            List<Reservation> userReservations = reservationService.deleteReservation(cod);
            return new ResponseEntity(userReservations, HttpStatus.CREATED);

        } else{
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);

        }
    }

}
