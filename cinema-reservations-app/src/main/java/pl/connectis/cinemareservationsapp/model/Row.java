package pl.connectis.cinemareservationsapp.model;

public class Row {
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
}
