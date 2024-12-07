package kg.example.application.mapper;

import kg.example.application.dto.PostImageCreateDto;
import kg.example.application.dto.PostImageDto;
import kg.example.application.dto.PostImageShortDto;
import kg.example.application.entity.PostImage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostImageMapper {
    PostImage toEntity(PostImageCreateDto postImageCreateDto);
    PostImageCreateDto toPostImageCreateDto(PostImage postImage);

    PostImage toEntity(PostImageShortDto postImageShortDto);
    PostImageShortDto toPostImageShortDto(PostImage postImage);

    PostImage toEntity(PostImageDto postImageDto);
    PostImageDto toPostImageDto(PostImage postImage);
}