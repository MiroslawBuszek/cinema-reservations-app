package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.service.UserService;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/myaccount")
    public UserDTO currentUserNameSimple() {

        return userService.getUser();

    }

    @PutMapping("/myaccount")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.CREATED);

    }

    @GetMapping("all")
    public Iterable<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/client")
    public User getUserByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("user {username=" + username + "} was not found");
        }
        return user;
    }



}
