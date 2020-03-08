package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SessionDTO {

    private long id;

    private long movieId;

    private long roomId;

    private ArrayList<Integer> reservedSeats;

    private LocalDateTime startTime;

}

