package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TicketDTO {

    private long id;

    private long sessionId;

    private long clientId;

    private int rowNumber;

    private int seatNumber;

    private double price;

}
