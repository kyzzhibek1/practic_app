package kg.example.application.controller;

import kg.example.application.dto.PostCreationDto;
import kg.example.application.dto.PostDto;
import kg.example.application.dto.PostEditDto;
import kg.example.application.dto.PostImageShortDto;
import kg.example.application.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAllPosts(Pageable pageable) {
        Page<PostDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/my")
    public ResponseEntity<Page<PostDto>> getCurrentUserPosts(Pageable pageable) {
        Page<PostDto> posts = postService.getCurrentUserPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostDto>> getPostsByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<PostDto> posts = postService.getPostsByUserId(userId, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreationDto postCreationDto) {
        PostDto createdPost = postService.createPost(postCreationDto);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostEditDto postEditDto) {
        PostDto updatedPost = postService.updatePost(id, postEditDto);
        return ResponseEntity.ok(updatedPost);
    }

    @PostMapping("/{postId}/images")
    public ResponseEntity<Void> addPostImageToPost(@PathVariable Long postId, @RequestBody PostImageShortDto postImageDto) {
        postService.addPostImageToPost(postId, postImageDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/images/{postImageId}")
    public ResponseEntity<Void> deletePostImageByItsId(@PathVariable Long postImageId) {
        postService.deletePostImageByItsId(postImageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/favourites")
    public ResponseEntity<PostDto> addToFavorites(@PathVariable Long postId) {
        PostDto favouritePost = postService.addToFavorites(postId);
        return ResponseEntity.ok(favouritePost);
    }

    @DeleteMapping("/{postId}/favourites")
    public ResponseEntity<PostDto> removeFromFavorites(@PathVariable Long postId) {
        PostDto favouritePost = postService.removeFromFavorites(postId);
        return ResponseEntity.ok(favouritePost);
    }
}
