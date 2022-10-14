package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper2 {

    public static UserDto userToUserDto(User user) {
        return new UserDto(user.getName(), user.getEmail());
    }

    public static User userDtoToUser(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail());
    }


}
