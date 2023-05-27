package ua.bugaienko.instazoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergii Bugaienko
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(String msg) {
        super(msg);
    }
}
