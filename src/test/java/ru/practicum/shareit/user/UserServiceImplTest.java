package ru.practicum.shareit.user;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

//    @Test
//    @Transactional
//    void addUser() {
//        UserDto userDto = new UserDto("username", "user@user.ru");
//        userService.addUser(userDto);
//        TypedQuery<User> query = em.createQuery("Select r from User r where r.name = :username", User.class);
//        User user = query.setParameter("username", userDto.getName()).getSingleResult();
//        Assertions.assertEquals(user.getId(), 1);
//        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
//    }
//
//    @Test
//    @Transactional
//    void updateUser() {
//        UserDto userDto = new UserDto("username", "user@user.ru");
//        User user = userService.addUser(userDto);
//
//        userDto.setName("newusername");
//        userService.updateUser(user.getId(), userDto);
//
//        TypedQuery<User> query = em.createQuery("Select r from User r where r.id = :id", User.class);
//        User userDb = query.setParameter("id", user.getId()).getSingleResult();
//        Assertions.assertEquals(userDb.getName(), "newusername");
//    }

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
}

