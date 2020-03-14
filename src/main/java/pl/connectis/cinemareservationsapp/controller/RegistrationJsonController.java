package pl.connectis.cinemareservationsapp.controller;

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

@RestController
@RequestMapping
public class RegistrationJsonController extends RegistrationBaseController {

    @Autowired
    UserService userService;

    @PostMapping(value = "add/client", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addClient(@Valid @RequestBody UserDTO userDTO) {

        encodePassword(userDTO);
        addUser(userDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
