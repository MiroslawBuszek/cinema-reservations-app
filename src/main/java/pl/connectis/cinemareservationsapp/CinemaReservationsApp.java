package pl.connectis.cinemareservationsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CinemaReservationsApp {

	public static void main(String[] args) {
		SpringApplication.run(CinemaReservationsApp.class, args);
	}

}
