package pl.connectis.cinemareservationsapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.connectis.cinemareservationsapp.mapper.SessionMapper;
import pl.connectis.cinemareservationsapp.mapper.TicketMapper;
import pl.connectis.cinemareservationsapp.mapper.UserMapper;

@Configuration
public class AppConfig {

    @Bean
    public SessionMapper sessionMapper() {
        return new SessionMapper();
    }

    @Bean
    public TicketMapper ticketMapper() {
        return new TicketMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

}
