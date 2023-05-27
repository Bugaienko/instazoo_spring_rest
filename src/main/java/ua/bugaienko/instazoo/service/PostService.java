package ua.bugaienko.instazoo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.bugaienko.instazoo.dto.PostDTO;
import ua.bugaienko.instazoo.entity.ImageModel;
import ua.bugaienko.instazoo.entity.Post;
import ua.bugaienko.instazoo.entity.User;
import ua.bugaienko.instazoo.exceptions.PostNotFoundException;
import ua.bugaienko.instazoo.repository.ImageRepository;
import ua.bugaienko.instazoo.repository.PostRepository;
import ua.bugaienko.instazoo.repository.UsersRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final ImageRepository imageRepository;

    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);


    @Autowired
    public PostService(PostRepository postRepository, UsersRepository usersRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.usersRepository = usersRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);

        LOG.info("Saving new post for User: {}", user.getEmail());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for user " + user.getEmail()));

    }

    public List<Post> getAllPostForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    @Transactional
    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));
        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username)).findAny();
        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, Principal principal) {
        Post post = getPostById(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
        LOG.info("Deleting post with id:{}", postId);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return usersRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }


}
