package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        User savedUser = userRepository.save(user);
        log.info("user {username=" + savedUser.getUsername() + "} was added: " + savedUser.toString());
        return userMapper.mapDTOFromEntity(savedUser);
    }

    public UserDTO getLoggedUser() {
        User user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        UserDTO userDTO = userMapper.mapDTOFromEntity(user);
        return userDTO;
    }

    public UserDTO updateUser(UserDTO userDTO) {
        if (!userDTO.getUsername().equals(authenticationFacade.getAuthentication().getName())) {
            throw new BadRequestException(
                    "{username=" + userDTO.getUsername() + "} does not correspond to the logged user");
        }
        User savedUser = userRepository.save(mapUserFromDTO(userDTO));
        log.info("user {id=" + savedUser.getUsername() + "} was updated: " + savedUser.toString());
        return userMapper.mapDTOFromEntity(savedUser);
    }

    private User mapUserFromDTO(UserDTO userDTO) {
        User user = userMapper.mapUserFromDTO(userDTO);
        user.setPassword(userRepository.findByUsername(user.getUsername()).getPassword());
        user.setActive(userRepository.findByUsername(user.getUsername()).getActive());
        user.setRole(userRepository.findByUsername(user.getUsername()).getRole());
        return user;
    }

    public List<UserDTO> getClientByExample(Map<String, String> requestParam) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(requestParam.get("username"));
        userDTO.setFirstName(requestParam.get("firstName"));
        userDTO.setLastName(requestParam.get("lastName"));
        if (requestParam.containsKey("birthDate")) {
            userDTO.setBirthDate(LocalDate.parse(requestParam.get("birthDate")));
        }
        User user = userMapper.mapClientFromDTO(userDTO);
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<User> userExample = Example.of(user, caseInsensitiveExampleMatcher);
        return userMapper.mapDTOFromEntity(userRepository.findAll(userExample));
    }

}
