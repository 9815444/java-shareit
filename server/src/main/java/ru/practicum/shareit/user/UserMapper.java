package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {

    public static UserDto userToUserDto(User user) {
        return new UserDto(user.getName(), user.getEmail());
    }

    public static User userDtoToUser(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail());
    }


}
