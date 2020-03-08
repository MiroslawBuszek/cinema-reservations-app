package pl.connectis.cinemareservationsapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;

import java.util.Arrays;
import java.util.List;


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
        this.userRepository.deleteAll();
        User admin = new User(1, "admin", passwordEncoder.encode("admin"), 1, "ADMIN", "ADMIN_ACCESS", "Termos", "Termiszewski", "termos@cinema.pl", 30, null, null);
        User firstEmployee = new User(2, "empl1", passwordEncoder.encode("empl1"), 1, "EMPLOYEE", "EMPLOYEE_ACCESS", "Dorota", "Termiszewska", "epml1@cinema.pl", 30, null, null);
        User client = new User(3, "client", passwordEncoder.encode("client"), 1, "CLIENT", "CLIENT_ACCESS", "Jan", "Nowak", "client@test.pl", 25, null, null);
        List<User> users = Arrays.asList(admin, firstEmployee, client);
        this.userRepository.saveAll(users);

        // Default Room
        this.roomRepository.deleteAll();
        int[] layout = {5, 5};
        Room room = new Room(1, 10, layout);
        this.roomRepository.save(room);
    }
}
