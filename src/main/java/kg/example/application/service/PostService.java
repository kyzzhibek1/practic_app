package kg.example.application.service;

import kg.example.application.dto.PostCreationDto;
import kg.example.application.dto.PostDto;
import kg.example.application.dto.PostEditDto;
import kg.example.application.dto.PostImageShortDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDto> getAllPosts(Pageable pageable);
    Page<PostDto> getCurrentUserPosts(Pageable pageable);
    Page<PostDto> getPostsByUserId(Long id, Pageable pageable);
    PostDto getPostById(Long id);
    PostDto createPost(PostCreationDto postCreationDto);
    PostDto updatePost(Long id, PostEditDto postEditDto);
    void addPostImageToPost(Long postId, PostImageShortDto postCreationDto);
    void deletePostImageByItsId(Long postImageId);
    void deletePost(Long id);
    PostDto addToFavorites(Long postId);
    PostDto removeFromFavorites(Long postId);
}
