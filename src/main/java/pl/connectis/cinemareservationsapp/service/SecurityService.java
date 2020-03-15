package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.mapper.UserMapper;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.security.IAuthenticationFacade;

@Slf4j
@Service
public class SecurityService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public UserDTO createAccount(UserDTO userDTO, boolean isEmployee) {

        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
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

        userRepository.save(user);
        log.info("User added: " + user.toString());

        return userDTO;

    }

}
