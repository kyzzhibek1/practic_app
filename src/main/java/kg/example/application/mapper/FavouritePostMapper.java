package kg.example.application.mapper;

import kg.example.application.dto.ShortFavouritePostDto;
import kg.example.application.entity.FavouritePost;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FavouritePostMapper {
    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "postTitle", target = "post.title")
    @Mapping(source = "userUsername", target = "user.username")
    @Mapping(source = "userId", target = "user.id")
    FavouritePost toEntity(ShortFavouritePostDto shortFavouritePostDto);

    @InheritInverseConfiguration(name = "toEntity")
    ShortFavouritePostDto toShortFavouritePostDto(FavouritePost favouritePost);
}