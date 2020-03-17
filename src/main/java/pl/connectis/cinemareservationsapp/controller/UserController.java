package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/myaccount")
    public ResponseEntity<UserDTO> getUser() {

        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);

    }

    @PutMapping("/myaccount")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);

    }

    @GetMapping("/client")
    public ResponseEntity<List<UserDTO>> getUserByExample(@RequestParam Map<String, String> requestParam) {

        return new ResponseEntity<>(userService.getUserByExample(requestParam), HttpStatus.OK);

    }

}
