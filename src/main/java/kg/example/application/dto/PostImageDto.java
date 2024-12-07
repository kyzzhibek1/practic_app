package kg.example.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostImageDto {
    private Long id;
    private Long postId;
    private String imageUrl;
    private String description;
    private LocalDateTime createdAt;
}