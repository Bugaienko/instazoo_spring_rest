package ua.bugaienko.instazoo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import ua.bugaienko.instazoo.entity.enums.ERole;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Sergii Bugaienko
 */

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(length = 3000)
    private String password;

//    @ElementCollection(targetClass = ERole.class)
//    @CollectionTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "users_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }


}
