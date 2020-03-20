package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.service.SessionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getSessionsByExample(@RequestParam Map<String, String> requestParams) {

        return new ResponseEntity<>(sessionService.getSessionsByExample(requestParams), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {

        return new ResponseEntity<>(sessionService.save(sessionDTO), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<SessionDTO> updateSession(@RequestBody SessionDTO sessionDTO) {

        return new ResponseEntity<>(sessionService.updateById(sessionDTO), HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteSession(@RequestParam Long id) {

        sessionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }




}
