package pl.connectis.cinemareservationsapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.service.SessionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<SessionDTO> getSessionsByExample(@RequestParam Map<String, String> requestParams) {
        return sessionService.getSessionsByExample(requestParams);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDTO createSession(@RequestBody SessionDTO sessionDTO) {
        return sessionService.save(sessionDTO);
    }

    @PutMapping
    public SessionDTO updateSession(@RequestParam Long id, @RequestBody SessionDTO sessionDTO) {
        return sessionService.updateById(id, sessionDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@RequestParam Long id) {
        sessionService.deleteById(id);
    }




}
