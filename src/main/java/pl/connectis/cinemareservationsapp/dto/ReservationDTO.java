package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.connectis.cinemareservationsapp.model.Seat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReservationDTO {

    @Positive
    private Long sessionId;

    @NotEmpty
    private List<Seat> reservedSeats;

}
