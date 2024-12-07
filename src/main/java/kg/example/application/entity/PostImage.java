package kg.example.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "posts_images")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "post_image_seq", sequenceName = "post_image_seq", allocationSize = 1)
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_image_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String description;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
