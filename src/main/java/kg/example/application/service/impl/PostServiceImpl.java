package kg.example.application.service.impl;

import kg.example.application.dto.*;
import kg.example.application.entity.Post;
import kg.example.application.entity.PostImage;
import kg.example.application.entity.User;
import kg.example.application.mapper.PostMapper;
import kg.example.application.repository.PostRepository;
import kg.example.application.repository.UserRepository;
import kg.example.application.service.FavouritePostService;
import kg.example.application.service.PostImageService;
import kg.example.application.service.PostService;
import kg.example.application.util.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostImageService postImageService;
    private final FavouritePostService favouritePostService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostImageService postImageService, FavouritePostService favouritePostService, UserRepository userRepository, PostRepository postRepository, PostMapper postMapper) {
        this.postImageService = postImageService;
        this.favouritePostService = favouritePostService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toPostDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto> getCurrentUserPosts(Pageable pageable) {
        User user = userRepository.findById(UserUtils.getCurrentUserId()).orElseThrow();
        List<Post> posts = user.getPosts();

        List<PostDto> postDtos = posts.stream()
                .map(postMapper::toPostDto)
                .toList();

        return getPageFromList(postDtos, pageable);
    }

    @Override
    public Page<PostDto> getPostsByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow();

        List<Post> posts = user.getPosts();

        List<PostDto> postDtos = posts.stream()
                .map(postMapper::toPostDto)
                .toList();

        return getPageFromList(postDtos, pageable);
    }

    private Page<PostDto> getPageFromList(List<PostDto> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        if (start > list.size()) {
            return Page.empty(pageable);
        }

        List<PostDto> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageable, list.size());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        return postMapper.toPostDto(post);
    }

    @Override
    public PostDto createPost(PostCreationDto postCreationDto) {
        Post post = new Post();
        post.setTitle(postCreationDto.getTitle());
        post.setContent(postCreationDto.getContent());

        User user = userRepository.findById(UserUtils.getCurrentUserId()).orElseThrow();
        post.setAuthor(user);

        Post savedPost = postRepository.save(post);
        if (postCreationDto.getImages() != null) {
            List<PostImage> images = postCreationDto.getImages().stream()
                    .map(postImageDto -> {
                        PostImage postImage = postImageService.createPostImage(postImageDto);
                        postImage.setPost(savedPost);
                        return postImage;
                    })
                    .toList();

            postImageService.saveAll(images);

            savedPost.setImages(images);
        }

        return postMapper.toPostDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long id, PostEditDto postEditDto) {
        User user = UserUtils.getCurrentUser();
        Post post = postRepository.findByIdAndAuthorId(id, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("You cannot update alien posts!"));

        if (postEditDto.getTitle() != null) {
            post.setTitle(postEditDto.getTitle());
        }
        if (postEditDto.getContent() != null) {
            post.setContent(postEditDto.getContent());
        }

        post = postRepository.save(post);
        return postMapper.toPostDto(post);
    }

    @Override
    public void addPostImageToPost(Long postId, PostImageShortDto postCreationDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostImage postImage = postImageService.createPostImage(postCreationDto);
        post.addImage(postImage);
        post = postRepository.save(post);
        postImage.setPost(post);
        postImageService.save(postImage);
    }

    @Override
    public void deletePostImageByItsId(Long postImageId) {
        postImageService.deletePostImage(postImageId);
    }

    @Override
    public void deletePost(Long id) {
        User user = UserUtils.getCurrentUser();

        Post post = postRepository.findByIdAndAuthorId(id, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("You cannot delete alien posts!"));

        postRepository.delete(post);
    }

    @Override
    public PostDto addToFavorites(Long postId) {
        ShortFavouritePostDto shortFavouritePostDto = favouritePostService.addToFavourites(postId);
        Post post = postRepository.findById(shortFavouritePostDto.getPostId()).orElseThrow();
        return postMapper.toPostDto(post);
    }

    @Override
    public PostDto removeFromFavorites(Long postId) {
        ShortFavouritePostDto shortFavouritePostDto = favouritePostService.removeFromFavourites(postId);
        Post post = postRepository.findById(shortFavouritePostDto.getPostId()).orElseThrow();
        return postMapper.toPostDto(post);
    }
}
