package pl.connectis.cinemareservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.model.Role;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.service.UserService;

import java.time.LocalDate;


@Service
public class CommandLineRunnerInit implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) {

        addClients();
        // Default User
        userRepository.save(new User("admin@kino.pl", passwordEncoder.encode("admin"), 1, Role.EMPLOYEE, "EMPLOYEE_ACCESS", "Admin", "Adminowski", LocalDate.of(2000, 10, 01), null, null));

//        // Default Room
//        this.roomRepository.deleteAll();
//        int[] layout = {5, 5};
//        Room room = new Room(1, 10, layout);
//        this.roomRepository.save(room);
    }

    private void addClients() {

        userService.createAccount(new UserDTO("adrian.budny@poczta.pl", "adrianbudny",
                null, "Adrian", "Budny", LocalDate.of(1981, 2, 15)), false);
        userService.createAccount(new UserDTO("gracjan.pakulski@poczta.pl", "gracjanpakulski", null, "Gracjan", "Pakulski", LocalDate.of(2003, 5, 28)), false);
        userService.createAccount(new UserDTO("sylwester.lis@poczta.pl", "sylwesterlis", null, "Sylwester", "Lis", LocalDate.of(1999, 4, 4)), false);
        userService.createAccount(new UserDTO("marta.fabian@poczta.pl", "martafabian", null, "Marta", "Fabian", LocalDate.of(2010, 7, 30)), false);
        userService.createAccount(new UserDTO("boleslaw.sniegowski@poczta.pl", "boleslawsniegowski", null, "Bolesław", "Śniegowski", LocalDate.of(2006, 3, 7)), false);
        userService.createAccount(new UserDTO("diana.czajka@poczta.pl", "dianaczajka", null, "Diana", "Czajka", LocalDate.of(1973, 11, 16)), false);
        userService.createAccount(new UserDTO("filip.chmielewski@poczta.pl", "filipchmielewski", null, "Filip", "Chmielewski", LocalDate.of(1921, 8, 6)), false);
        userService.createAccount(new UserDTO("mateusz.wlodarczyk@poczta.pl", "mateuszwlodarczyk", null, "Mateusz", "Włodarczyk", LocalDate.of(2001, 1, 27)), false);

    }

    private void addEmployees() {

    }

}
