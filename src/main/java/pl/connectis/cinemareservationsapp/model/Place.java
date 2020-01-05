package pl.connectis.cinemareservationsapp.model;

import javax.persistence.*;

@Entity
public class Place {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    private Row row;

    public Place() {
    }

    public Place(long id, Row row) {
        this.id = id;
        this.row = row;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", row=" + row +
                '}';
    }
}
