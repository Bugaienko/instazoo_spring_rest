package ua.bugaienko.instazoo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.bugaienko.instazoo.entity.Post;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author Sergii Bugaienko
 */

@Data
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    @NotEmpty
    private String message;
    private String username;

}
