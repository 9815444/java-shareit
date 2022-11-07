package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Map;

//@Service
//@AllArgsConstructor
public class UserValidation {

    public static void userIsValidAdd(UserDto userDto, UserClient userClient) {
        if (userDto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is null.");
        }
        if (userDto.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is null.");
        }
        emailValidation(userDto.getEmail());
        duplicalEmailValidation(userDto.getEmail(), userClient);
    }

    private static void duplicalEmailValidation(String email, UserClient userClient) {
        List<Map> users = (List<Map>) userClient.getAllUsers().getBody();
        for (Map user : users) {
            if (email.equals(user.get("id"))) {
                throw new BadRequestException();
            }
        }
    }

    public static void emailValidation(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new BadRequestException();
        }
    }

    public static void userIsValidUpdate(UserDto userDto, UserClient userClient) {
        if (userDto.getEmail() != null) {
            emailValidation(userDto.getEmail());
        }
    }

    public static void userIsPresent(Long userId, UserClient userClient) {
        if (userClient.getUser(userId).getStatusCodeValue() == 404) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

}
