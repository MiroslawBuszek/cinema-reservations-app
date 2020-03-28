package pl.connectis.cinemareservationsapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.service.UserService;

import java.time.LocalDate;

@Component
@Profile("main")
public class MainInitializer implements CommandLineRunner {

    private final UserService userService;

    public MainInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.createAccount(
                new UserDTO("admin@kino.pl", "admin",
                        null, "Admin", "Adminowski",
                        LocalDate.of(1001, 01, 10)), true);
    }

}
