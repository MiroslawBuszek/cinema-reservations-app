package pl.connectis.cinemareservationsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Seat {

    @Positive
    private Integer rowNumber;

    @Positive
    private Integer seatNumber;

    private boolean isSold;

}
