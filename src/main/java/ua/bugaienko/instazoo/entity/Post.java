package ua.bugaienko.instazoo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sergii Bugaienko
 */

@Data
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;


    private String caption;

    private String location;

    private Integer likes;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,
            mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected  void onCreate() {
        this.createdDate = LocalDateTime.now();

    }

}
