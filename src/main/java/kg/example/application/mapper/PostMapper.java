package kg.example.application.mapper;

import kg.example.application.dto.PostDto;
import kg.example.application.entity.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "authorUsername", target = "author.username")
    Post toEntity(PostDto postDto);

    @AfterMapping
    default void linkImages(@MappingTarget Post post) {
        post.getImages().forEach(image -> image.setPost(post));
    }

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.username", target = "authorUsername")
    PostDto toPostDto(Post post);

    @AfterMapping
    default void linkImagesDto(@MappingTarget PostDto postDto) {
        postDto.getImages().forEach(image -> image.setPostId(postDto.getId()));
    }
}