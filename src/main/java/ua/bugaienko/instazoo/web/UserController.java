package ua.bugaienko.instazoo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.bugaienko.instazoo.dto.UserDTO;
import ua.bugaienko.instazoo.entity.User;
import ua.bugaienko.instazoo.facade.UserFacade;
import ua.bugaienko.instazoo.service.UserService;
import ua.bugaienko.instazoo.validations.ResponseErrorValidation;

import java.security.Principal;

/**
 * @author Sergii Bugaienko
 */

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;
    private final ResponseErrorValidation errorValidation;

    @Autowired
    public UserController(UserService userService, UserFacade userFacade, ResponseErrorValidation errorValidation) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.errorValidation = errorValidation;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDto(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

}
