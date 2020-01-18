package pl.connectis.cinemareservationsapp.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Session session;

    @ManyToOne
    @JoinColumn
    private Client client;

    private int rowNumber;

    private int seatNumber;

    private double price;

}
