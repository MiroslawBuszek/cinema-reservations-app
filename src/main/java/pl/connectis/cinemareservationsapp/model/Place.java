package pl.connectis.cinemareservationsapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Place {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
    private boolean isSold;

    public Place() {
    }

    public Place(long id, Room room, Session session, boolean isSold) {
        this.id = id;
        this.room = room;
        this.session = session;
        this.isSold = isSold;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", room=" + room +
                ", session=" + session +
                ", isSold=" + isSold +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return id == place.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
