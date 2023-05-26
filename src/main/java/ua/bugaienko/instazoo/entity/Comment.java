package ua.bugaienko.instazoo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Sergii Bugaienko
 */

@Data
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "text", nullable = false)
    private String message;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected  void onCreate() {
        this.createdDate = LocalDateTime.now();
    }


}
