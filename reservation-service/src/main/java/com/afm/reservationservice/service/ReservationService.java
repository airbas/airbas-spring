package com.afm.reservationservice.service;

import com.afm.reservationservice.repository.PassengerRepository;
import com.afm.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import model.flights.AirPlane;
import model.prenotation.Passenger;
import model.prenotation.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PassengerRepository passengerRepository;
    private final GenerateNameService generateReservationtName;

    public Reservation createReservation(String flightName, String airPlaneName, String seatCord,
                                         String rateName, Passenger passenger, String userMail) {
        Reservation reservation = new Reservation();
        //Rate rate = rateRepository.findByType(rateName);

        reservation.setAirPlaneName(airPlaneName);
        reservation.setFlightName(flightName);
        reservation.setRate(rateName);
        reservation.setPassenger(passenger);
        reservation.setUserEmail(userMail);
        reservation.setSeatCord(seatCord);

        passengerRepository.save(passenger);
        reservation.setPassenger(passenger);
        //BigDecimal result = flight.getPrice().add(rate.getPrice());
        //reservation.setPrice( result);
        reservationRepository.save(reservation);

        String ReservationName = generateReservationtName.generateReservationtName(reservation.getId());
        reservation.setName(ReservationName);
        reservationRepository.save(reservation);

        return reservation;
    }

    public List<Reservation> getReservation(String email){
        return reservationRepository.findByUserEmail(email);
    }

    public List<Reservation> deleteReservation(String email, String codRes){
        List<Reservation> userRes = reservationRepository.findByName(codRes);
        for(Reservation res: userRes){
            reservationRepository.delete(res);
        }
        return userRes;
    }

    public List<Reservation> getAll(){
        return reservationRepository.findAll();
    }
}
