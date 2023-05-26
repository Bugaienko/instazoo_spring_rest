package ua.bugaienko.instazoo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sergii Bugaienko
 */

@Data
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
