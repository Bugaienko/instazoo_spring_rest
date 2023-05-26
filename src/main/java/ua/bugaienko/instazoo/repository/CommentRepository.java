package ua.bugaienko.instazoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.bugaienko.instazoo.entity.Comment;
import ua.bugaienko.instazoo.entity.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
}
