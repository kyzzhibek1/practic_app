package kg.example.application.service.impl;

import kg.example.application.dto.PostImageDto;
import kg.example.application.dto.PostImageShortDto;
import kg.example.application.entity.PostImage;
import kg.example.application.entity.User;
import kg.example.application.mapper.PostImageMapper;
import kg.example.application.repository.PostImageRepository;
import kg.example.application.repository.UserRepository;
import kg.example.application.service.PostImageService;
import kg.example.application.util.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostImageServiceImpl implements PostImageService {

    private final PostImageRepository postImageRepository;

    private final PostImageMapper postImageMapper;

    private final UserRepository userRepository;

    public PostImageServiceImpl(PostImageRepository postImageRepository, PostImageMapper postImageMapper, UserRepository userRepository) {
        this.postImageRepository = postImageRepository;
        this.postImageMapper = postImageMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Page<PostImageDto> getAllPostImages(Pageable pageable) {
        Page<PostImage> postImages = postImageRepository.findAll(pageable);
        return postImages.map(postImageMapper::toPostImageDto);
    }

    @Override
    public Page<PostImageDto> getAllPostImagesByPostId(Long postId, Pageable pageable) {
        Page<PostImage> postImages = postImageRepository.findAllByPostId(postId, pageable);
        return postImages.map(postImageMapper::toPostImageDto);
    }

    @Override
    public PostImageDto getById(Long id) {
        return postImageMapper.toPostImageDto(postImageRepository.findById(id).orElseThrow());
    }

    @Override
    public PostImage createPostImage(PostImageShortDto postImageShortDto) {
        PostImage postImage = postImageMapper.toEntity(postImageShortDto);
        return postImageRepository.save(postImage);
    }

    @Override
    public PostImage save(PostImage image) {
        return postImageRepository.save(image);
    }

    @Override
    public List<PostImage> saveAll(List<PostImage> list) {
        return postImageRepository.saveAll(list);
    }

    @Override
    public void deletePostImage(Long id) {
        PostImage postImage = postImageRepository.findById(id).orElseThrow();
        User user = userRepository.findById(UserUtils.getCurrentUserId()).orElseThrow();
        if (!postImage.getPost().getAuthor().equals(user)) {
            throw new RuntimeException("You cannot delete image not from your post");
        };
        postImageRepository.delete(postImage);
    }
}
