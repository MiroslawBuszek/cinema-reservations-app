package pl.connectis.cinemareservationsapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.mapper.UserMapper;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.security.IAuthenticationFacade;
import pl.connectis.cinemareservationsapp.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping
public class SecurityController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @PostMapping("signup")
    public ResponseEntity<Void> addClient(@Valid @RequestBody UserDTO userDTO) {

        encodePassword(userDTO);
        log.info(userDTO.toString());
        addUser(userDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    void addUser(UserDTO userDTO) {

        if (userService.findByUsername(userDTO.getUsername()) != null) {
            throw new BadRequestException("user {username=" + userDTO.getUsername() + "} was found");
        }

        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapClientFromDTO(userDTO);
        userService.save(user);

    }

    UserDTO encodePassword(UserDTO userDTO) {

        userDTO.setEncodedPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setPassword(null);
        return userDTO;

    }

    String getUserEmail() {

        return authenticationFacade.getAuthentication().getName();

    }

}
