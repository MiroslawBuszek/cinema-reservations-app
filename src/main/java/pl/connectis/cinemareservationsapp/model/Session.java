package pl.connectis.cinemareservationsapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Session {
    @Id
    @GeneratedValue
    private long id;
    private Movie movie;
    private Room room;
    private LocalDateTime startTime;

    public Session() {
    }

    public Session(long id, Movie movie, Room room, LocalDateTime startTime) {
        this.id = id;
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", movie=" + movie +
                ", room=" + room +
                ", startTime=" + startTime +
                '}';
    }
}
