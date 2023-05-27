package ua.bugaienko.instazoo.facade;

import org.springframework.stereotype.Component;
import ua.bugaienko.instazoo.dto.UserDTO;
import ua.bugaienko.instazoo.entity.User;

/**
 * @author Sergii Bugaienko
 */

@Component
public class UserFacade {
    public UserDTO userToUserDto(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());

        return userDTO;
    }
}
