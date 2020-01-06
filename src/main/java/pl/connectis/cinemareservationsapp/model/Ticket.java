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

//    @OneToOne
//    @JoinColumn
//  TODO: Change String into instance of Place class
    private String place;
    private double price;

    public Ticket() {
    }

    public Ticket(long id, Session session, Client client, String place, double price) {
        this.id = id;
        this.session = session;
        this.client = client;
        this.place = place;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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
                ", place=" + place +
                ", price=" + price +
                '}';
    }
}
