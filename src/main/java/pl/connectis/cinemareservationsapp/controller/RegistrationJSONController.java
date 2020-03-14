package pl.connectis.cinemareservationsapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping
public class RegistrationJSONController extends RegistrationBaseController {

    @Autowired
    UserService userService;

    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addClient(@Valid @RequestBody UserDTO userDTO) {

        encodePassword(userDTO);
        log.info(userDTO.toString());
        addUser(userDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
