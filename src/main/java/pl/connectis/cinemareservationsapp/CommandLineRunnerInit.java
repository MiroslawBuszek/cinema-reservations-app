package pl.connectis.cinemareservationsapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;

import java.time.LocalDate;


@Service
public class CommandLineRunnerInit implements CommandLineRunner {
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private PasswordEncoder passwordEncoder;

    public CommandLineRunnerInit(UserRepository userRepository, PasswordEncoder passwordEncoder, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {

        // Default User
        userRepository.save(new User("admin@kino.pl", passwordEncoder.encode("admin"), 1, "EMPLOYEE", "EMPLOYEE_ACCESS", "Admin", "Adminowski", LocalDate.of(2000, 10, 01), null, null));

        // Default Room
        this.roomRepository.deleteAll();
        int[] layout = {5, 5};
        Room room = new Room(1, 10, layout);
        this.roomRepository.save(room);
    }
}
