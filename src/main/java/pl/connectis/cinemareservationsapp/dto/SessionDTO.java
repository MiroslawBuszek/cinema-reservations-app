package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SessionDTO {

    @Positive
    private Long id;

    @Positive
    private Long movieId;

    @Positive
    private Long roomId;

    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    private LocalTime startTime;

    @Positive
    private Double ticketPrice;

}

