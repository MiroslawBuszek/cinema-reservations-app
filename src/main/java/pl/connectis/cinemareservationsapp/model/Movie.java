package pl.connectis.cinemareservationsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    private Long id;

    @Column(nullable = false)
    private String title;

    private String category;

    @Column(nullable = false)
    @Positive
    private Integer length;

    private String description;

    @Column(nullable = false)
    @Positive
    private Integer ageLimit;

}
