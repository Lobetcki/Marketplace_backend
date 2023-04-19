package ru.skypro.homework.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.commentsDTO.CommentsDTO;
import ru.skypro.homework.model.Comments;

@Mapper
public interface CommentsMapper {
    @Mapping(source = "user.id", target = "authorId")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.userImage.url", target = "authorImageUrl")
    CommentsDTO fromCommentDto(Comments comments);

    @Mapping(source = "authorId", target = "user.id")
    @Mapping(source = "text", target = "text")
    Comments toComment(CommentsDTO commentsDTO);
}
