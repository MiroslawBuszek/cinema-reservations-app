package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.connectis.cinemareservationsapp.model.Seat;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TicketDTO {

    private Long id;

    private Long sessionId;

    private String client;

    private Seat seat;

}
