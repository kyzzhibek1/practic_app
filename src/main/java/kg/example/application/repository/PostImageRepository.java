package kg.example.application.repository;

import kg.example.application.entity.PostImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    Page<PostImage> findAllByPostId(Long postId, Pageable pageable);
}