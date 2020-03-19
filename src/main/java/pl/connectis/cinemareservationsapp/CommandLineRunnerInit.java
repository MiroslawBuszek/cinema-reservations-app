package pl.connectis.cinemareservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class CommandLineRunnerInit implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    MovieService movieService;

    @Autowired
    RoomService roomService;

    @Autowired
    SessionService sessionService;

    @Autowired
    TicketService ticketService;


    @Override
    public void run(String... args) {

        initializeDatabase();

    }

    private void initializeDatabase() {

        addClients();
        addEmployees();
        addMovies();
        addRooms();
        addSessions();
        addTickets();

    }

    private void addClients() {

        userService.createAccount(
                new UserDTO("adrian.budny@poczta.pl", "adrianbudny",
                null, "Adrian", "Budny",
                LocalDate.of(1981, 2, 15)), false);
        userService.createAccount(
                new UserDTO("gracjan.pakulski@poczta.pl", "gracjanpakulski",
                null, "Gracjan", "Pakulski",
                LocalDate.of(2003, 5, 28)), false);
        userService.createAccount(
                new UserDTO("sylwester.lis@poczta.pl", "sylwesterlis",
                null, "Sylwester", "Lis",
                LocalDate.of(1999, 4, 4)), false);
        userService.createAccount(
                new UserDTO("marta.fabian@poczta.pl", "martafabian",
                null, "Marta", "Fabian",
                LocalDate.of(2010, 7, 30)), false);
        userService.createAccount(
                new UserDTO("boleslaw.sniegowski@poczta.pl", "boleslawsniegowski",
                null, "Bolesław", "Śniegowski",
                LocalDate.of(2006, 3, 7)), false);
        userService.createAccount(
                new UserDTO("diana.czajka@poczta.pl", "dianaczajka",
                null, "Diana", "Czajka",
                LocalDate.of(1973, 11, 16)), false);
        userService.createAccount(
                new UserDTO("filip.chmielewski@poczta.pl", "filipchmielewski",
                null, "Filip", "Chmielewski",
                LocalDate.of(1921, 8, 6)), false);
        userService.createAccount(
                new UserDTO("mateusz.wlodarczyk@poczta.pl", "mateuszwlodarczyk",
                null, "Mateusz", "Włodarczyk",
                LocalDate.of(2001, 1, 27)), false);

    }

    private void addEmployees() {

        userService.createAccount(
                new UserDTO("rozalia.sikora@kino.pl", "rozaliasikora",
                        null, "Rozalia", "Sikora",
                        LocalDate.of(1994, 5, 27)), true);
        userService.createAccount(
                new UserDTO("franciszek.walentowicz@kino.pl", "franciszekwalentowicz",
                        null, "Franciszek", "Walentowicz",
                        LocalDate.of(1993, 10, 18)), true);
        userService.createAccount(
                new UserDTO("piotr.krakowski@kino.pl", "piotrkrakowski",
                        null, "Piotr", "Krakowski",
                        LocalDate.of(1997, 6, 9)), true);

    }

    private void addMovies() {

        movieService.save(
                new Movie(1, "Gwiezdne wojny: Skywalker. Odrodzenie", "Familijny", 142,
                        "Członkowie organizacji Ruchu Oporu ponownie stawiają czoła Najwyższemu Porządkowi.",
                        10));
        movieService.save(
                new Movie(2, "Jak zostałem gangsterem. Historia prawdziwa", "Akcja", 140,
                        "Historia najniebezpieczniejszego gangstera w Polsce, dla którego władza, bycie " +
                                "ponad stan i pieniądze stanowią priorytet.", 15));
        movieService.save(
                new Movie(3, "Judy", "Dramat", 118,
                        "Zima 1968 roku. Ciesząca się ogromną popularnością Judy Garland przybywa do " +
                                "Londynu na serię koncertów.", 15));
        movieService.save(
                new Movie(4, "Oficer i szpieg", "Thriller szpiegowski", 132,
                        "Historia francuskiego kapitana Alfreda Dreyfusa, który został niesłusznie " +
                                "oskarżony o zdradę stanu i skazany na dożywotnie więzienie.", 15));
        movieService.save(
                new Movie(5, "Jumanji: Przygoda w dżungli", "Familijny", 123,
                        "Wielki powrót bohaterów „Jumanji: Przygoda w dżungli”! Pierwsza część filmu " +
                                "stała się międzynarodowym przebojem, zarabiając na całym świecie ponad 960 milionów " +
                                "dolarów.", 12));

    }

    private void addRooms() {

        roomService.save(new Room(1, 154, "22,22,22,22,22,22,22"));
        roomService.save(new Room(2, 50, "10,10,10,10,10"));
        roomService.save(new Room(3, 300, "30,30,30,30,30,30,30,30,30,30"));
        roomService.save(new Room(4, 90, "15,15,15,15,15,15"));

    }

    private void addSessions() {

        sessionService.save(new SessionDTO(1, 3, 1,
                LocalDateTime.of(2020, 4, 10, 15, 00)));
        sessionService.save(new SessionDTO(2, 2, 2,
                LocalDateTime.of(2020, 4, 2, 16, 00)));
        sessionService.save(new SessionDTO(3, 1, 4,
                LocalDateTime.of(2020, 4, 2, 17, 00)));
        sessionService.save(new SessionDTO(4, 4, 1,
                LocalDateTime.of(2020, 4, 2, 18, 00)));
        sessionService.save(new SessionDTO(5, 2, 3,
                LocalDateTime.of(2020, 4, 2, 18, 30)));
        sessionService.save(new SessionDTO(6, 2, 2,
                LocalDateTime.of(2020, 4, 2, 21, 00)));
        sessionService.save(new SessionDTO(7, 2, 3,
                LocalDateTime.of(2020, 4, 2, 21, 00)));

    }

    private void addTickets() {

        ticketService.save(new TicketDTO(1, 4, "adrian.budny@poczta.pl", 4, 10,14.99));
        ticketService.save(new TicketDTO(2, 4, "filip.chmielewski@poczta.pl", 3, 10, 14.99));
        ticketService.save(new TicketDTO(3, 4, "filip.chmielewski@poczta.pl", 3, 11, 14.99));
        ticketService.save(new TicketDTO(4, 2, "boleslaw.sniegowski@poczta.pl", 5, 8, 19.99));
        ticketService.save(new TicketDTO(5, 2, "boleslaw.sniegowski@poczta.pl", 5, 9, 19.99));
        ticketService.save(new TicketDTO(6, 1, "sylwester.lis@poczta.pl", 2, 5, 19.99));
        ticketService.save(new TicketDTO(7, 1, "sylwester.lis@poczta.pl", 2, 6, 19.99));
        ticketService.save(new TicketDTO(8, 1, "sylwester.lis@poczta.pl", 2, 7, 19.99));
        ticketService.save(new TicketDTO(9, 1, "boleslaw.sniegowski@poczta.pl", 4, 10, 14.99));
        ticketService.save(new TicketDTO(10, 6, "marta.fabian@poczta.pl", 3, 6, 14.99));
        ticketService.save(new TicketDTO(11, 7, "diana.czajka@poczta.pl", 6, 16, 14.99));
        ticketService.save(new TicketDTO(12, 7, "diana.czajka@poczta.pl", 6, 17, 14.99));
        ticketService.save(new TicketDTO(13, 5, "filip.chmielewski@poczta.pl", 7, 14, 14.99));
        ticketService.save(new TicketDTO(14, 5, "filip.chmielewski@poczta.pl", 7, 15, 14.99));
        ticketService.save(new TicketDTO(15, 1, "marta.fabian@poczta.pl", 4, 11, 14.99));

    }

}
