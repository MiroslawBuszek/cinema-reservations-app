package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.service.SecurityService;

import javax.validation.Valid;

@RestController
@RequestMapping
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> addClient(@Valid @RequestBody UserDTO userDTO) {

        return new ResponseEntity<>(securityService.createAccount(userDTO, false), HttpStatus.CREATED);

    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> addEmployee(@Valid @RequestBody UserDTO userDTO) {

        return new ResponseEntity<>(securityService.createAccount(userDTO, true), HttpStatus.CREATED);

    }

}
