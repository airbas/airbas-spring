package model.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RemoveBookSeat implements Serializable {
    private String flightName;
    private String seatCord;

    public RemoveBookSeat(String flightName, String seatCord) {
        this.flightName = flightName;
        this.seatCord = seatCord;
    }
}
