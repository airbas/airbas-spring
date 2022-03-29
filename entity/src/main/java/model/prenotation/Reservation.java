package model.prenotation;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String name;
    private String flightName;
    private String seatCord;
    private String airPlaneName;

    private String dapartureAirport;
    private String arrivalAirport;
    private Date date;

    //private BigDecimal price;
    //@OneToOne
    //private Rate rate;
    private String rate;
    @OneToOne
    private Passenger passenger;
}
