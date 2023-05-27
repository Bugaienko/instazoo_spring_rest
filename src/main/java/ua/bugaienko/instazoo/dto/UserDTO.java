package ua.bugaienko.instazoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sergii Bugaienko
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty
    private String firstname;

    private String lastname;

//    @NotEmpty
    private String username;

    private String bio;

}
