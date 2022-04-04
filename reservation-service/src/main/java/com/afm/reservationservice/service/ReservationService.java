package com.afm.reservationservice.service;

import com.afm.reservationservice.messages.RabbitMqSender;
import com.afm.reservationservice.repository.PassengerRepository;
import com.afm.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import model.flights.AirPlane;
import model.prenotation.Passenger;
import model.prenotation.Reservation;
import model.utils.RemoveBookSeat;
import model.utils.ReservationRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PassengerRepository passengerRepository;
    private final GenerateNameService generateReservationtName;
    private final RabbitMqSender rabbitMqSender;

    public Reservation buildReservation(ReservationRequest req) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        Reservation reservation = new Reservation();

        Passenger p = new Passenger();
        p.setBirthdate(df.parse(req.getPassengerDate()));
        p.setFirstname(req.getPassangerName());
        p.setSecondname(req.getPassangerSurname());
        p.setTelephone(req.getPassangerPhone());
        //Rate rate = rateRepository.findByType(rateName);

        reservation.setAirPlaneName(req.getAirPlaneName());
        reservation.setFlightName(req.getFlightName());
        reservation.setRate(req.getRate());
        reservation.setUserEmail(req.getUsermail());
        reservation.setSeatCord(req.getSeatCord());
        reservation.setArrivalAirport(req.getArrivalAirport());
        reservation.setDapartureAirport(req.getDapartureAirport());
        reservation.setDate(df.parse(req.getDate()));

        passengerRepository.save(p);
        reservation.setPassenger(p);
        reservationRepository.save(reservation);

        reservation.setName(generateReservationtName.generateReservationtName(reservation.getId()));
        reservationRepository.save(reservation);

        return reservation;
    }

    public List<Reservation> getReservation(String email){
        return reservationRepository.findByUserEmail(email);
    }

    public List<Reservation> deleteReservation(String codRes){
        List<Reservation> userRes = reservationRepository.findByName(codRes);
        for(Reservation res: userRes){
            RemoveBookSeat msg = rabbitMqSender.buildMsg(res.getAirPlaneName(), res.getSeatCord());
            System.out.println(msg.toString());
            rabbitMqSender.send(msg);
            reservationRepository.delete(res);
        }
        return userRes;
    }

}
