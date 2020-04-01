package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.mapper.TicketMapper;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;
import pl.connectis.cinemareservationsapp.repository.TicketRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.security.AuthenticationFacade;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TicketService {

    private final AuthenticationFacade authenticationFacade;
    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    public TicketService(AuthenticationFacade authenticationFacade,
                         TicketRepository ticketRepository,
                         SessionRepository sessionRepository,
                         UserRepository userRepository,
                         TicketMapper ticketMapper) {
        this.authenticationFacade = authenticationFacade;
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<TicketDTO> getTicketsByExample(Map<String, String> requestParam) {
        Ticket ticket = new Ticket();
        if (requestParam.containsKey("id")) {
            ticket.setId(Long.parseLong(requestParam.get("id")));
        }
        ticket.setUser(userRepository.findByUsername(requestParam.get("client")));
        if (requestParam.containsKey("session")) {
            ticket.setSession(getSessionById(Long.parseLong(requestParam.get("session"))));
        }
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Ticket> ticketExample = Example.of(ticket, caseInsensitiveExampleMatcher);
        return ticketMapper.mapDTOFromEntity(ticketRepository.findAll(ticketExample));
    }

    public List<TicketDTO> getMyTicketsByExample(Map<String, String> requestParam) {
        String client = authenticationFacade.getAuthentication().getName();
        requestParam.remove("client");
        requestParam.put("client", client);
        return getTicketsByExample(requestParam);
    }

    public void deleteById(Long id) {
        validateTicketExists(id);
        ticketRepository.deleteById(id);
        log.info("ticket {id=" + id + "} was deleted");
    }

    private void validateTicketExists(Long ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new ResourceNotFoundException("ticket {id=" + ticketId + "} was not found");
        }
    }

    private Session getSessionById(Long sessionId) {
        if (sessionRepository.findById(sessionId).isPresent()) {
            return sessionRepository.findById(sessionId).get();
        }
        throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
    }

}
