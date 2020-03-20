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

    private Long id;

    private Long sessionId;

    private String client;

    private Integer rowNumber;

    private Integer seatNumber;

    private Double price;

}
