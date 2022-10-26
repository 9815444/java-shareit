package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class UserValidationTest {

    private EntityManager em;

    private RequestService requestService;
    private RequestRepository requestRepository;
    private UserRepository userRepository;
    private UserValidation userValidation;
    private UserService userService;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
//        when(userRepository.save(any())).then(invocation -> invocation.getArgument(0));
        userValidation = new UserValidation(userRepository);
    }

    @Test
    void testUserIsValid() {
        //Assign
        UserDto userDto = new UserDto("username", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");

        userValidation.userIsValidAdd(userDto);
    }

    @Test
    void testUserIsNotValidName() {
        //Assign
        UserDto userDto = new UserDto(null, "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            userValidation.userIsValidAdd(userDto);
        });

        assertEquals("400 BAD_REQUEST \"Name is null.\"", exception.getMessage());
    }

    @Test
    void testUserIsNotValidEmail() {
        //Assign
        UserDto userDto = new UserDto("name", null);
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            userValidation.userIsValidAdd(userDto);
        });

        assertEquals("400 BAD_REQUEST \"Email is null.\"", exception.getMessage());
    }

    @Test
    void testUserIBadEmail() {
        //Assign
        UserDto userDto = new UserDto("name", "user");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");

        Exception exception = assertThrows(BadRequestException.class, () -> {
            userValidation.userIsValidAdd(userDto);
        });
    }

    @Test
    void testUserIsValidUpdate() {
        //Assign
        UserDto userDto = new UserDto("name", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");

        userValidation.userIsValidUpdate(userDto);
    }

    @Test
    void testUserIsPresent() {
        //Assign
        UserDto userDto = new UserDto("name", "user");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            userValidation.userIsPresent(1L);
        });
    }


}
