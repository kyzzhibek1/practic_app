package kg.example.application.service.impl;

import kg.example.application.dto.ShortFavouritePostDto;
import kg.example.application.entity.FavouritePost;
import kg.example.application.entity.Post;
import kg.example.application.entity.User;
import kg.example.application.mapper.FavouritePostMapper;
import kg.example.application.repository.FavouritePostRepository;
import kg.example.application.repository.PostRepository;
import kg.example.application.repository.UserRepository;
import kg.example.application.service.FavouritePostService;
import kg.example.application.util.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class FavouritePostServiceImpl implements FavouritePostService {

    private final PostRepository postRepository;
    private final FavouritePostRepository favouritePostRepository;
    private final FavouritePostMapper favouritePostMapper;
    private final UserRepository userRepository;

    public FavouritePostServiceImpl(PostRepository postRepository, FavouritePostRepository favouritePostRepository, UserRepository userRepository, FavouritePostMapper favouritePostMapper, UserRepository userRepository1) {
        this.postRepository = postRepository;
        this.favouritePostRepository = favouritePostRepository;
        this.favouritePostMapper = favouritePostMapper;
        this.userRepository = userRepository1;
    }

    @Override
    public ShortFavouritePostDto addToFavourites(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User user = userRepository.findById(UserUtils.getCurrentUserId()).orElseThrow();

        boolean alreadyFavourite = favouritePostRepository.existsByUserAndPost(user, post);
        if (alreadyFavourite) {
            throw new IllegalArgumentException("Post already in favourites");
        }

        FavouritePost favouritePost = new FavouritePost();
        favouritePost.setPost(post);
        favouritePost.setUser(user);
        favouritePost = favouritePostRepository.save(favouritePost);

        return favouritePostMapper.toShortFavouritePostDto(favouritePost);
    }

    @Override
    public ShortFavouritePostDto removeFromFavourites(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User user = userRepository.findById(UserUtils.getCurrentUserId()).orElseThrow();

        FavouritePost favouritePost = favouritePostRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new IllegalArgumentException("Favourite post not found"));

        favouritePostRepository.delete(favouritePost);

        return favouritePostMapper.toShortFavouritePostDto(favouritePost);
    }
}
