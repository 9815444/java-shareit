package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserValidation;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemValidationTest {

    private ItemValidation itemValidation;

    private UserValidation userValidation;

    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        userValidation = mock(UserValidation.class);
//        when(userValidation.userIsPresent().findById(any())).thenReturn(Optional.of(item));
        itemValidation = new ItemValidation(userValidation, itemRepository);
    }

    @Test
    void testItemIsValidAdd() {

//        when(requestRepository.findById(any())).thenReturn(Optional.of(request));

        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidAdd(null, itemDto);
        });

        itemDto.setAvailable(null);
        var exception1 = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidAdd(1L, itemDto);
        });

        itemDto.setDescription("");
        var exception2 = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidAdd(1L, itemDto);
        });

        itemDto.setDescription(null);
        var exception3 = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidAdd(1L, itemDto);
        });

        itemDto.setName("");
        var exception4 = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidAdd(1L, itemDto);
        });

        itemDto.setName(null);
        var exception5 = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidAdd(1L, itemDto);
        });

    }

    @Test
    void testItemIsValidUpdate() {

        var exception5 = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidUpdate(null, 1L);
        });

    }

    @Test
    void testItemIsValidUpdate2() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        var exception = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidDelete(1L, 1L);
        });

    }

    @Test
    void testItemIsValidUpdate3() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 2L, null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));

        var exception = assertThrows(ResponseStatusException.class, () -> {
            itemValidation.itemIsValidDelete(1L, 1L);
        });

    }

    @Test
    void testItemIsValidUpdate4() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));

       itemValidation.itemIsValidDelete(1L, 1L);

    }

    @Test
    void testItemIsValidUpdate5() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));

        itemValidation.itemIsValidUpdate(1L, 1L);

    }
}
