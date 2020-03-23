package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.mapper.UserMapper;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.security.AuthenticationFacade;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService {

    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(AuthenticationFacade authenticationFacade,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserMapper userMapper) {
        this.authenticationFacade = authenticationFacade;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createAccount(UserDTO userDTO, boolean isEmployee) {

        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new BadRequestException("user {username=" + userDTO.getUsername() + "} was found");
        }

        userDTO.setEncodedPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setPassword(null);
        User user;

        if (isEmployee) {
            user = userMapper.mapEmployeeFromDTO(userDTO);
        } else {
            user = userMapper.mapClientFromDTO(userDTO);
        }

        userDTO.setEncodedPassword(null);
        userRepository.save(user);
        log.info("User added: " + user.toString());

        return userDTO;

    }

    public UserDTO getLoggedUser() {

        User user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        UserDTO userDTO = userMapper.mapDTOFromEntity(user);
        return userDTO;

    }

    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {

        if (userDTO.getUsername() != null && !userDTO.getUsername().equals(authenticationFacade.getAuthentication().getName())) {
            throw new BadRequestException("{username=" + userDTO.getUsername() + "} does not correspond to the logged user");
        }

        User existingUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());;

        if (userDTO.getFirstName() != null) {
            existingUser.setFirstName(userDTO.getFirstName());
        }

        if (userDTO.getLastName() != null) {
            existingUser.setLastName(userDTO.getLastName());
        }

        if (userDTO.getBirthDate() != null) {
            existingUser.setBirthDate(userDTO.getBirthDate());
        }

        return userMapper.mapDTOFromEntity(existingUser);

    }

    public List<UserDTO> getClientByExample(Map<String, String> requestParam) {

        UserDTO userDTO = new UserDTO();

        if (requestParam.containsKey("username")) {
            userDTO.setUsername(requestParam.get("username"));
        }

        if (requestParam.containsKey("firstName")) {
            userDTO.setFirstName(requestParam.get("firstName"));
        }

        if (requestParam.containsKey("lastName")) {
            userDTO.setLastName(requestParam.get("lastName"));
        }

        if (requestParam.containsKey("birthDate")) {
            userDTO.setBirthDate(LocalDate.parse(requestParam.get("birthDate")));
        }

        User user = userMapper.mapClientFromDTO(userDTO);
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<User> userExample = Example.of(user, caseInsensitiveExampleMatcher);
        return userMapper.mapDTOFromEntity(userRepository.findAll(userExample));

    }

}
