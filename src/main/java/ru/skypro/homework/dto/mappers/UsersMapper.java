package ru.skypro.homework.dto.mappers;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.model.Users;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mapping(source = "users.userImage.url", target = "userImageUrl")
    UserDTO toUserDto(Users users);
}
