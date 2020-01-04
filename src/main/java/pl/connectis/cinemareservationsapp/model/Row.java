package pl.connectis.cinemareservationsapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Row {
    @Id
    @GeneratedValue
    private long id;
    Room room;
    private int capacity;
    private boolean hasSold;

    public Row() {
    }

    public Row(long id, Room room, int capacity, boolean hasSold) {
        this.id = id;
        this.room = room;
        this.capacity = capacity;
        this.hasSold = hasSold;
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

    public boolean isHasSold() {
        return hasSold;
    }

    public void setHasSold(boolean hasSold) {
        this.hasSold = hasSold;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", room=" + room +
                ", capacity=" + capacity +
                ", hasSold=" + hasSold +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return id == row.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
