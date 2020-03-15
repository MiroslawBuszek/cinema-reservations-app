package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.mapper.UserMapper;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.security.IAuthenticationFacade;

@Slf4j
@Service
public class UserService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    UserRepository userRepository;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public UserDTO getUser() {

        User user = findByUsername(authenticationFacade.getAuthentication().getName());

        UserMapper userMapper = new UserMapper();
        UserDTO userDTO = userMapper.mapDTOFromEntity(user);

        return userDTO;

    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {

        log.info(user.toString());
        return userRepository.save(user);
    }

    public Iterable<User> saveAll(Iterable<User> userList) {
        return userRepository.saveAll(userList);
    }

    @Transactional
    public User updateById(String username, User user) {
        User existingUser = userRepository.findByUsername(username);
        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getBirthDate() != null) {
            existingUser.setBirthDate(user.getBirthDate());
        }
        //Todo add User
        return existingUser;
    }

//    public void deleteById(String id) {
//        userRepository.deleteById(id);
//    }

}
