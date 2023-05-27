package ua.bugaienko.instazoo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.bugaienko.instazoo.dto.CommentDTO;
import ua.bugaienko.instazoo.entity.Comment;
import ua.bugaienko.instazoo.entity.Post;
import ua.bugaienko.instazoo.entity.User;
import ua.bugaienko.instazoo.exceptions.CommentNotFoundException;
import ua.bugaienko.instazoo.exceptions.PostNotFoundException;
import ua.bugaienko.instazoo.repository.CommentRepository;
import ua.bugaienko.instazoo.repository.PostRepository;
import ua.bugaienko.instazoo.repository.UsersRepository;

import java.security.Principal;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;

    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);


    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UsersRepository usersRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Saving new comment fo post with id: {}", post.getId());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));
        List<Comment> comments = commentRepository.findAllByPost(post);

        return comments;
    }

    @Transactional
    public void deleteComment(Long commentId, Principal principal) {
        User user = getUserByPrincipal(principal);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment cannot be found"));
        if (comment.getUserId().equals(user.getId())) {
            commentRepository.delete(comment);
        }
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return usersRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }
}
