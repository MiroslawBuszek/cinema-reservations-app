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
    public ResponseEntity<UserDTO> addClient(@Valid @RequestBody UserDTO userDTO) {

        addUser(userDTO, false);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);

    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> addEmployee(@Valid @RequestBody UserDTO userDTO) {

        addUser(userDTO, true);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);

    }

    void addUser(UserDTO userDTO, boolean isEmployee) {

        if (userService.findByUsername(userDTO.getUsername()) != null) {
            throw new BadRequestException("user {username=" + userDTO.getUsername() + "} was found");
        }

        userDTO.setEncodedPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setPassword(null);
        UserMapper userMapper = new UserMapper();
        User user;

        if (isEmployee) {
            user = userMapper.mapEmployeeFromDTO(userDTO);
        } else {
            user = userMapper.mapClientFromDTO(userDTO);
        }

        userService.save(user);
        log.info("User added: " + user.toString());

    }


    String getUserEmail() {

        return authenticationFacade.getAuthentication().getName();

    }

}
