package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.security.IAuthenticationFacade;
import pl.connectis.cinemareservationsapp.service.UserService;

import javax.validation.Valid;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    @PostMapping("/registration/client")
    public ResponseEntity<User> addUserClient(@Valid @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {                   //todo
            throw new BadRequestException("user {username=" + user.getUsername() + "} was found");
        }
        if (user.getRoles().equals("CLIENT") == false){
            throw new BadRequestException("{Incorrect role: this is not a customer}");
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }


    @PostMapping("/registration/employee")
    public ResponseEntity<User> addUserEmployee(@Valid @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {                   //todo
            throw new BadRequestException("user {username=" + user.getUsername() + "} was found");
        }
        if (user.getRoles().equals("EMPLOYEE") == false){
            throw new BadRequestException("{Incorrect role: this is not an employee}");
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping
    public User getUserById(@RequestParam long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("user {id=" + id + "} was not found");
        }
        return user;
    }


    @PostMapping("/many")
    public ResponseEntity<Iterable> addUserList(@Valid @RequestBody Iterable<User> userList) {
        for (User user : userList) {
            if (userService.findByUsername(user.getUsername()) != null) {
                throw new BadRequestException("user {username=" + user.getUsername() + "} was found");
            }
        }
        return new ResponseEntity<>(userService.saveAll(userList), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestParam long id, @Valid @RequestBody User user) {
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("user {id=" + id + "} was not found");
        } else if (!user.getUsername().equals(existingUser.getUsername())) {
            throw new BadRequestException("{username=" + user.getUsername() +
                    "} does not correspond to user of {id=" + id + "}");
        } else {
            return new ResponseEntity<>(userService.updateById(id, user), HttpStatus.CREATED);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestParam long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("user {id=" + id + "} was not found");
        }
        userService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
