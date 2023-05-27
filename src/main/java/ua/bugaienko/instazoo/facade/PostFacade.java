package ua.bugaienko.instazoo.facade;

import org.springframework.stereotype.Component;
import ua.bugaienko.instazoo.dto.PostDTO;
import ua.bugaienko.instazoo.entity.Post;

/**
 * @author Sergii Bugaienko
 */

@Component
public class PostFacade {

    public PostDTO postToPostDTO(Post post) {

        PostDTO postDTO = new PostDTO();
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setId(post.getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setTitle(post.getTitle());
        postDTO.setLikes(post.getLikes());
        postDTO.setUsersLiked(post.getLikedUsers());
        postDTO.setLocation(post.getLocation());

        return postDTO;
    }
}
