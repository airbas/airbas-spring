package com.afm.reservationservice.controller;

import com.afm.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import model.prenotation.Reservation;
import model.utils.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/reservation")
@RestController
@RequiredArgsConstructor
@CrossOrigin

public class ReservationController {
    private static Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    @PostMapping("/creates")
    public List<Reservation> createReservations(@RequestBody List<ReservationRequest> requests) throws ParseException {
        List<Reservation> reservations = new LinkedList<>();
        logger.info("Creation Reservations" );
        for (ReservationRequest request : requests){
            reservations.add(reservationService.buildReservation(request));
        }
        return reservations;
    }

    @CrossOrigin
    @GetMapping ("/get/{email}")
    public List<Reservation> getReservations(@PathVariable String email){
        return reservationService.getReservation(email);
    }

    @GetMapping ("/getall")
    public List<Reservation> getAll(){
        return reservationService.getAll();
    }

    @GetMapping ("/delete/{email}/{cod}")
    public List<Reservation> deleteReservations(@PathVariable String email, @PathVariable String cod){
        return reservationService.deleteReservation(cod);
    }

}
