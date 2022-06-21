package com.example.restreword.dto.mapper;

import com.example.restreword.dto.PostInput;
import com.example.restreword.dto.PostOutput;
import com.example.restreword.entity.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "dest", source = "description")
    PostInput toInputDto(Post post);
    @InheritInverseConfiguration
    Post inputToEntity(PostInput post);
    PostOutput toOutputDto(Post post);
    List<PostOutput> toOutputDtos(List<Post> posts);
}
