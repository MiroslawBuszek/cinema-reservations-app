package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.connectis.cinemareservationsapp.security.IAuthenticationFacade;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TicketService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketMapper ticketMapper;

    public List<TicketDTO> getTicketsByExample(Map<String, String> requestParam) {

        TicketDTO ticketDTO = new TicketDTO();

        if (requestParam.containsKey("id")) {
            ticketDTO.setId(Long.parseLong(requestParam.get("id")));
        }

        if (requestParam.containsKey("session")) {
            ticketDTO.setSessionId(Long.parseLong(requestParam.get("session")));
        }

        if (requestParam.containsKey("client")) {
            ticketDTO.setClient(requestParam.get("client"));
        }

        Ticket ticket = ticketMapper.mapEntityFromDTO(ticketDTO);
        log.info(ticketDTO.toString());
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Ticket> ticketExample = Example.of(ticket, caseInsensitiveExampleMatcher);
        return ticketMapper.mapDTOFromEntity(ticketRepository.findAll(ticketExample));

    }

    public List<TicketDTO> getMyTicketsByExample(Map<String, String> requestParam) {

        String client = authenticationFacade.getAuthentication().getName();

        if (requestParam.containsKey("client")) {
            requestParam.remove("client");
        }

        requestParam.put("client", client);
        return getTicketsByExample(requestParam);

    }

    public TicketDTO save(TicketDTO ticketDTO) {

        ticketRepository.save(ticketMapper.mapEntityFromDTO(ticketDTO));
        return ticketDTO;

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

    private Session getSession(Long sessionId) {

        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);

        if (!sessionOptional.isPresent()) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }

        return sessionOptional.get();

    }

}
