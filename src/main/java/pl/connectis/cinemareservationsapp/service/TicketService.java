package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.mapper.TicketMapper;
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
            ticket.setSession(sessionRepository.findById(Long.parseLong(requestParam.get("session"))).get());
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
    }

    private void validateTicketExists(Long ticketId) {
        if (!ticketRepository.findById(ticketId).isPresent()) {
            throw new ResourceNotFoundException("ticket {id=" + ticketId + "} was not found");
        }
    }

    private Ticket mapEntityFromDTO(TicketDTO ticketDTO) {
        Ticket ticket = ticketMapper.mapEntityFromDTO(ticketDTO);
        ticket.setUser(userRepository.findByUsername(ticketDTO.getClient()));
        if (sessionRepository.findById(ticketDTO.getSessionId()).isPresent()) {
            ticket.setSession(sessionRepository.findById(ticketDTO.getSessionId()).get());
        }
        return ticket;
    }

}
