package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.errors.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDto2;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.RequestServiceImpl;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class RequestServiceImplTest {

    private RequestRepository requestRepository;

    private UserRepository userRepository;

    private RequestServiceImpl requestService;

    @Test
    void testAddRequest() {

        requestRepository = mock(RequestRepository.class);
        userRepository = mock(UserRepository.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository);

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDto2 itemDto2 = new ItemDto2(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.save(any())).thenReturn(request);

        var result = requestService.addRequest(user.getId(), requestDto);
        assertNotNull(result);

    }

    @Test
    void testFindRequests() {

        requestRepository = mock(RequestRepository.class);
        userRepository = mock(UserRepository.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository);

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDto2 itemDto2 = new ItemDto2(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.findByUserId(any())).thenReturn(List.of(request));

        var result = requestService.findRequests(user.getId());
        assertNotNull(result);
        assertEquals(request.getDescription(), result.get(0).getDescription());
    }

    @Test
    void testFindRequest() {

        requestRepository = mock(RequestRepository.class);
        userRepository = mock(UserRepository.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository);

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDto2 itemDto2 = new ItemDto2(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.findById(any())).thenReturn(Optional.of(request));

        var result = requestService.findRequest(user.getId(), request.getId());
        assertNotNull(result);
        assertEquals(request.getDescription(), result.getDescription());
    }

    @Test
    void testFindAllRequests() {

        requestRepository = mock(RequestRepository.class);
        userRepository = mock(UserRepository.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository);

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDto2 itemDto2 = new ItemDto2(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        int fromPage = 0;
        int size = 10;
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size);

        var page = new PageImpl<Request>(List.of(request), pageable, List.of(request).size());

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.findAllByUserIdNot(any(), any())).thenReturn(page);

        var result = requestService.findAllRequests(user.getId(), 0L, 10L);
        assertNotNull(result);

    }

    @Test
    void testFindAllRequestsNotFound() {

        requestRepository = mock(RequestRepository.class);
        userRepository = mock(UserRepository.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository);

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Exception exception = assertThrows(NotFoundException.class, () -> {
            var result = requestService.findAllRequests(1L, 0L, 10L);
        });

    }

    @Test
    void testFindAllNoFromAndSize() {

        requestRepository = mock(RequestRepository.class);
        userRepository = mock(UserRepository.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository);

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDto2 itemDto2 = new ItemDto2(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        int fromPage = 0;
        int size = 10;
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size);

        var page = new PageImpl<Request>(List.of(request), pageable, List.of(request).size());

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.findAllByUserIdNot(any())).thenReturn(List.of(request));

        var result = requestService.findAllRequests(user.getId(), null, null);
        assertNotNull(result);

    }

    @Test
    void testFindAllRequestsBadSize() {

        requestRepository = mock(RequestRepository.class);
        userRepository = mock(UserRepository.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository);

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDto2 itemDto2 = new ItemDto2(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        int fromPage = 0;
        int size = 10;
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size);

        var page = new PageImpl<Request>(List.of(request), pageable, List.of(request).size());

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.findAllByUserIdNot(any(), any())).thenReturn(page);

        Exception exception = Assertions.assertThrows(BadRequestException.class, () -> {
            var result = requestService.findAllRequests(user.getId(), -10L, 10L);
        });

    }

}
