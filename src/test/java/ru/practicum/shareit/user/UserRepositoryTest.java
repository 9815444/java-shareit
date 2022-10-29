package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.model.User;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUserId() {
        var user = new User(null, "name", "email@mail.ru");
        var newUser = userRepository.save(user);
        var findedUser = userRepository.findById(newUser.getId());
        Assertions.assertEquals(newUser.getName(), findedUser.get().getName());
    }

}
