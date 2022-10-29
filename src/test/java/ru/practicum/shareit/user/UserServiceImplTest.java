package ru.practicum.shareit.user;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.errors.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImplTest {

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
        when(userRepository.save(any())).then(invocation -> invocation.getArgument(0));

        userValidation = mock(UserValidation.class);
//        when(userValidation.userIsValidAdd(any())).then("");

        userService = new UserServiceImpl(userRepository, userValidation);
    }

    @Test
    @Transactional
    void addUser() {

        //Assign
        UserDto userDto = new UserDto("username", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //Act
        var result = userService.addUser(userDto);

        //Assert
        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());

    }

    @Test
    @Transactional
    void updateUser() {

        //Assign
        UserDto userDto = new UserDto("username", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //Act
        var result = userService.updateUser(user.getId(), userDto);

        //Assert
        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());

    }

    @Test
    @Transactional
    void deleteUser() {

        //Assign
        UserDto userDto = new UserDto("username", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");
//        doNothing().when(userRepository).delete(any());

        doNothing().when(userRepository).delete(any());

//        var users = mock(UserService.class);
//        when(users.findUser(any())).thenReturn(user);
//        doCallRealMethod().when(users).deleteUser(any());

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //Act
        userService.deleteUser(user.getId());

        //Assert
//        assertNotNull(result);
//        assertEquals(userDto.getName(), result.getName());

    }

    @Test
    @Transactional
    void findUser() {

        //Assign
        UserDto userDto = new UserDto("username", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //Act
        var result = userService.findUser(user.getId());

        //Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());

    }

    @Test
    @Transactional
    void findUserNotFound() {

        //Assign
        UserDto userDto = new UserDto("username", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        //Act
//        var result = userService.findUser(user.getId());

        //Assert
//        assertNotNull(result);
//        assertEquals(user.getId(), result.getId());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            var result = userService.findUser(user.getId());
        });

//        String expectedMessage = "For input string";
//        String actualMessage = exception.getMessage();

    }

    @Test
    void testDeleteUser() {
        //Assign
        UserDto userDto = new UserDto("username", "user@user.ru");
        var user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@test.com");

        doNothing().when(userRepository).delete(any());
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //Act

        userService.deleteUser(user.getId());
    }


}

