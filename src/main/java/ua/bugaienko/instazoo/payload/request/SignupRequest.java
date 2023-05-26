package ua.bugaienko.instazoo.payload.request;

import lombok.Data;
import ua.bugaienko.instazoo.anotations.PasswordMatches;
import ua.bugaienko.instazoo.anotations.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Sergii Bugaienko
 */

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;

    @NotEmpty(message = "Please enter your name")
    private String firstname;

    @NotEmpty(message = "Please enter your lastname")
    private String lastname;

    @NotEmpty(message = "Please enter your username")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;


    private String confirmPassword;

}
