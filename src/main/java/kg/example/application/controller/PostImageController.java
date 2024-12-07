package kg.example.application.controller;

import kg.example.application.dto.PostImageDto;
import kg.example.application.service.PostImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-images")
public class PostImageController {

    private final PostImageService postImageService;

    public PostImageController(PostImageService postImageService) {
        this.postImageService = postImageService;
    }

    @GetMapping
    public ResponseEntity<Page<PostImageDto>> getAllPostImages(Pageable pageable) {
        Page<PostImageDto> postImages = postImageService.getAllPostImages(pageable);
        return ResponseEntity.ok(postImages);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<PostImageDto>> getAllPostImagesByPostId(@PathVariable Long postId, Pageable pageable) {
        Page<PostImageDto> postImages = postImageService.getAllPostImagesByPostId(postId, pageable);
        return ResponseEntity.ok(postImages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostImageDto> getPostImageById(@PathVariable Long id) {
        PostImageDto postImageDto = postImageService.getById(id);
        return ResponseEntity.ok(postImageDto);
    }

}
