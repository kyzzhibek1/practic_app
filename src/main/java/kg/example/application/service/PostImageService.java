package kg.example.application.service;

import kg.example.application.dto.PostImageDto;
import kg.example.application.dto.PostImageShortDto;
import kg.example.application.entity.PostImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostImageService {
    Page<PostImageDto> getAllPostImages(Pageable pageable);

    Page<PostImageDto> getAllPostImagesByPostId(Long postId, Pageable pageable);

    PostImageDto getById(Long id);

    PostImage createPostImage(PostImageShortDto postImageShortDto);

    PostImage save(PostImage image);

    List<PostImage> saveAll(List<PostImage> list);

    void deletePostImage(Long id);
}
