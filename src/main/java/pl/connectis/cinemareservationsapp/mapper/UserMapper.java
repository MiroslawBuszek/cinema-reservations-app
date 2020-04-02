package pl.connectis.cinemareservationsapp.mapper;

import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.model.Role;
import pl.connectis.cinemareservationsapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public User mapClientFromDTO(UserDTO userDTO) {
        User user = mapUserFromDTO(userDTO);
        user.setRole(Role.CLIENT);
        user.setActive(1);
        return user;
    }

    public User mapEmployeeFromDTO(UserDTO userDTO) {
        User user = mapUserFromDTO(userDTO);
        user.setRole(Role.EMPLOYEE);
        user.setActive(1);
        return user;
    }

    public User mapUserFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getEncodedPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthDate(userDTO.getBirthDate());
        return user;
    }

    public UserDTO mapDTOFromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setBirthDate(user.getBirthDate());
        return userDTO;
    }

    public List<UserDTO> mapDTOFromEntity(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(mapDTOFromEntity(user));
        }
        return userDTOs;
    }

}
