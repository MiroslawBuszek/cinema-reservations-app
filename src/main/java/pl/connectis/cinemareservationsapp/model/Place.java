//package pl.connectis.cinemareservationsapp.model;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//
//@Entity
//public class Place {
//    @Id
//    @GeneratedValue
//    private long id;
//    private Room room;
//    private Row row;
//    private boolean isSold;
//
//    public Place() {
//    }
//
//    public Place(long id, Room room, Row row, boolean isSold) {
//        this.id = id;
//        this.room = room;
//        this.row = row;
//        this.isSold = isSold;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }
//
//    public Row getRow() {
//        return row;
//    }
//
//    public void setRow(Row row) {
//        this.row = row;
//    }
//
//    public boolean isSold() {
//        return isSold;
//    }
//
//    public void setSold(boolean sold) {
//        isSold = sold;
//    }
//
//    @Override
//    public String toString() {
//        return "Place{" +
//                "id=" + id +
//                ", room=" + room +
//                ", row=" + row +
//                ", isSold=" + isSold +
//                '}';
//    }
//}
