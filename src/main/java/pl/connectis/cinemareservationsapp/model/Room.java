package pl.connectis.cinemareservationsapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private long id;
    private int capacity;
    private int[] layout;

    public Room() {
    }

    public Room(long id, int capacity, int[] layout) {
        this.id = id;
        this.capacity = capacity;
        this.layout = layout;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int[] getLayout() {
        return layout;
    }

    public void setLayout(int[] layout) {
        this.layout = layout;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", layout=" + Arrays.toString(layout) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
