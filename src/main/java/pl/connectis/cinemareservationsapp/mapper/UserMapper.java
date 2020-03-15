package pl.connectis.cinemareservationsapp.mapper;

import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.model.User;

public class UserMapper {

    public User mapClientFromDTO(UserDTO userDTO) {

        User user = mapEntityFromDTO(userDTO);

        user.setRoles("CLIENT");
        user.setPermissions("CLIENT_ACCESS");
        user.setActive(1);

        return user;

    }

    public User mapEmployeeFromDTO(UserDTO userDTO) {

        User user = mapEntityFromDTO(userDTO);

        user.setRoles("EMPLOYEE");
        user.setPermissions("EMPLOYEE_ACCESS");
        user.setActive(1);

        return user;

    }

    private User mapEntityFromDTO(UserDTO userDTO) {

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

}
