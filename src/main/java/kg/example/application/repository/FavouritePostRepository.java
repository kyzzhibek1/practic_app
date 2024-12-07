package kg.example.application.repository;

import kg.example.application.entity.FavouritePost;
import kg.example.application.entity.Post;
import kg.example.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouritePostRepository extends JpaRepository<FavouritePost, Long> {
    boolean existsByUserAndPost(User user, Post post);

    Optional<FavouritePost> findByUserAndPost(User user, Post post);
}