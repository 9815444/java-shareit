package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@AllArgsConstructor
public class UserValidation {

    private final UserRepository repository;

    public void userIsValidAdd(UserDto userDto) {
        if (userDto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is null.");
        }
        if (userDto.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is null.");
        }
        emailValidation(userDto.getEmail());
    }

    public void emailValidation(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new BadRequestException();
        }
    }

    public void userIsValidUpdate(UserDto userDto) {
        if (userDto.getEmail() != null) {
            emailValidation(userDto.getEmail());
        }
    }

    public void userIsPresent(Long userId) {
        if (!repository.findById(userId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

}
