package ua.bugaienko.instazoo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sergii Bugaienko
 */

@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {

    private boolean success;
    private String token;
}
