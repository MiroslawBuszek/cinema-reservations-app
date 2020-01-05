//package pl.connectis.cinemareservationsapp.model;
//
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//
//@Entity
//public class Client {
//    @Id
//    @GeneratedValue
//    private long id;
//    private String firstName;
//    private String lastName;
//    private int age;
//    private String email;
//
//    public Client() {
//    }
//
//    public Client(long id, String firstName, String lastName, int age, String email) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//        this.email = email;
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
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    @Override
//    public String toString() {
//        return "Client{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", age=" + age +
//                ", email='" + email + '\'' +
//                '}';
//    }
//}
