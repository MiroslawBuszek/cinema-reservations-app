package pl.connectis.cinemareservationsapp.model;

import javax.persistence.*;
import java.util.Arrays;

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int[] getLayout() {
        return layout;
    }

    public void setLayout(int[] layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", layout=" + Arrays.toString(layout) +
                '}';
    }

}
