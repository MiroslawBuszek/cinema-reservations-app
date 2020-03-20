package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SessionDTO {

    private Long id;

    private Long movieId;

    private Long roomId;

    private LocalDate startDate;

    private LocalTime startTime;

    private Double ticketPrice;

}

