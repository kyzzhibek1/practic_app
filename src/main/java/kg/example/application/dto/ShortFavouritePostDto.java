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
public class ShortFavouritePostDto {
    private Long id;
    private Long userId;
    private String userUsername;
    private Long postId;
    private String postTitle;
    private LocalDateTime createdAt;
}