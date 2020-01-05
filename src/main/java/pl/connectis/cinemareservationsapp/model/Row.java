package pl.connectis.cinemareservationsapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Row {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    private Room room;

    private int capacity;

    public Row() {
    }

    public Row(long id, Room room, int capacity, boolean hasSold) {
        this.id = id;
        this.room = room;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", room=" + room +
                ", capacity=" + capacity +
                '}';
    }
}
