package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;
    private final UserRepository1 repository;

    public void userIsValidAdd(UserDto userDto) {
        if (userDto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is null.");
        }
        if (userDto.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is null.");
        }
        emailValidation(userDto.getEmail());
    }

    private void emailValidation(String email) {
        if (userRepository.findAll().stream().collect(Collectors.toList())
                    .stream().filter((user -> user.getEmail().equals(email))).count() != 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is present.");
        }

    }

    public void userIsValidUpdate(UserDto userDto) {
        if (userDto.getEmail() != null) {
            emailValidation(userDto.getEmail());
        }

    }

    public void userIsPresent(Long userId) {
        if (!repository.findById(userId).isPresent()) {
//        if (userRepository.find(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

}
