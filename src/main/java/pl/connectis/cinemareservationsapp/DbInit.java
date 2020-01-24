package pl.connectis.cinemareservationsapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Use_r;
import pl.connectis.cinemareservationsapp.repository.UserRepository;

import java.util.Arrays;
import java.util.List;


@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        this.userRepository.deleteAll();

        //test users
        Use_r admin = new Use_r("admin", passwordEncoder.encode("admin"), "EMPLOYEE", "EMPLOYEE_ACCESS");
        Use_r client = new Use_r("client", passwordEncoder.encode("client"), "CLIENT", "CLIENT_ACCESS");

        List<Use_r> users = Arrays.asList(admin, client);
        this.userRepository.saveAll(users);
    }
}
