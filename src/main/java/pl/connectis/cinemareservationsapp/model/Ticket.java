package pl.connectis.cinemareservationsapp.model;

import javax.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    private Session session;

    @ManyToOne
    @JoinColumn
    private Client client;

    private int rowNumber;
    private int seatNumber;
    private double price;

    public Ticket() {
    }

    public Ticket(long id, Session session, Client client, int rowNumber, int seatNumber, double price) {
        this.id = id;
        this.session = session;
        this.client = client;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", session=" + session +
                ", client=" + client +
                ", rowNumber=" + rowNumber +
                ", seatNumber=" + seatNumber +
                ", price=" + price +
                '}';
    }

}
