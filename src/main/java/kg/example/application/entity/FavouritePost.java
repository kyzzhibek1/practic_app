package kg.example.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "favourites_posts")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "favourite_post_seq", sequenceName = "favourite_post_seq", allocationSize = 1)
public class FavouritePost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favourite_post_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}

