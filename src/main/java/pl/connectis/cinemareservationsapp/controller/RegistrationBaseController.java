package pl.connectis.cinemareservationsapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.mapper.UserMapper;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.service.UserService;

@RequestMapping
abstract class RegistrationBaseController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    void addUser(UserDTO userDTO) {

        if (userService.findByUsername(userDTO.getUsername()) != null) {
            throw new BadRequestException("user {username=" + userDTO.getUsername() + "} was found");
        }

        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapFromDTO(userDTO);
        userService.save(user);

    }

    UserDTO encodePassword(UserDTO userDTO) {

        userDTO.setEncodedPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setPassword(null);
        return userDTO;

    }

}
