package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.connectis.cinemareservationsapp.model.Seat;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReservationDTO {

    private Long sessionId;

    private List<Seat> reservedSeats;

}
