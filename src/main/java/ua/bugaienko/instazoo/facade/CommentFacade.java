package ua.bugaienko.instazoo.facade;

import org.springframework.stereotype.Component;
import ua.bugaienko.instazoo.dto.CommentDTO;
import ua.bugaienko.instazoo.entity.Comment;

/**
 * @author Sergii Bugaienko
 */

@Component
public class CommentFacade {

    public CommentDTO commentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setMessage(comment.getMessage());
        commentDTO.setUsername(comment.getUsername());

        return commentDTO;
    }
}
