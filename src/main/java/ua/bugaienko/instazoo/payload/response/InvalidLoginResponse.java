package ua.bugaienko.instazoo.payload.response;

import lombok.Getter;

/**
 * @author Sergii Bugaienko
 */

@Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }
}
