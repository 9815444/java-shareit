package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.errors.NotFound;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

        private final UserRepository1 repository;
    private final UserMapper userMapper;
    private final UserValidation validation;

//    public UserServiceImpl(UserRepository1 repository) {
//        this.repository = repository;
//    }

    @Override
    public User addUser(UserDto userDto) {
        validation.userIsValidAdd(userDto);
        User user = userMapper.userDtoToUser(userDto);
//        return userRepository.add(user);
        User user1 = repository.save(userMapper.userDtoToUser(userDto));
        return user1;
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {

        validation.userIsValidUpdate(userDto);

        User user = findUser(id);
        User updatedUser = new User(id, user.getName(), user.getEmail());

        if (userDto.getEmail() != null) {
            updatedUser.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            updatedUser.setName(userDto.getName());
        }

        return repository.save(updatedUser);

//        return userRepository.update(id, updatedUser);

    }

    @Override
    public void deleteUser(Long id) {
        repository.delete(findUser(id));
//        userRepository.delete(id);
    }

    @Override
    public User findUser(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFound();
        }
//        return userRepository.find(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
//        return userRepository.findAll();
    }
}
