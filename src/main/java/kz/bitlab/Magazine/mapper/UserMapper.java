package kz.bitlab.Magazine.mapper;

import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto (Users users);
    Users toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<Users> userList);
    List<Users> toEntityList(List<UserDto> userDtoList);
}
