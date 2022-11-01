package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.errors.NotFoundException;
import ru.practicum.shareit.errors.UnsupportedStatusException;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
public class BookingServiceImplTest {

    private BookingServiceImpl bookingService;

    private BookingRepository bookingRepository;

    private UserRepository userRepository;

    private CommentRepository commentRepository;

    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        userRepository = mock(UserRepository.class);
        commentRepository = mock(CommentRepository.class);
        itemRepository = mock(ItemRepository.class);
        bookingService = new BookingServiceImpl(bookingRepository, userRepository, commentRepository, itemRepository);
    }

    @Test
    void testFindUser() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        var result = bookingService.findItem(1L);

        assertEquals(item.getName(), result.getName());

    }

    @Test
    void testFindNotFound() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(null));

       Exception exception = assertThrows(NotFoundException.class, () -> {
           var result = bookingService.findUser(1L);
       });

    }

    @Test
    void testStatus() {
        var status = Status.APPROVED;
    }

    @Test
    void testFindLast() {
        var result = bookingService.findLast(null, false);
        assertNull(result);
    }

    @Test
    void testFindNext() {
        var result = bookingService.findNext(null, false);
        assertNull(result);
    }

    @Test
    void testFindNextList() {
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(null);
        booking.setEnd(null);
        booking.setStatus("test");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(null);
        bookingDto.setEnd(null);

        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));
        var result = bookingService.findNext(item.getId(), true);
        assertEquals(booking.getId(), result.getId());
    }

    @Test
    void testFindUserNotFound() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            var result = bookingService.findItem(1L);
        });

    }

    @Test
    void testFindUserItems() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(null);
        booking.setEnd(null);
        booking.setStatus("test");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(null);
        bookingDto.setEnd(null);

        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));

        var result = bookingService.findUserItems(1L);

    }

    @Test
    void testAdd() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(2), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus("test");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        var result = bookingService.add(1L, bookingDto);

//        assertEquals(booking.getId(), result.getId());

    }

    @Test
    void testAddBadRequest() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(2), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus("test");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(BadRequestException.class, () -> {
            var result = bookingService.add(1L, bookingDto);
        });

    }

    @Test
    void testAddBadRequest2() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(2), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
//        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
//        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus("test");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
//        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
//        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Exception exception = assertThrows(BadRequestException.class, () -> {
            var result = bookingService.add(1L, bookingDto);
        });

    }

    @Test
    void testAddBadNotFound() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(2), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
//        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
//        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus("test");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
//        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
//        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Exception exception = assertThrows(BadRequestException.class, () -> {
            var result = bookingService.add(3L, bookingDto);
        });

    }

    @Test
    void testApprove() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.WAITING.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        var result = bookingService.approve(1L, 1L, true);

//        Exception exception = assertThrows(BadRequestException.class, () -> {
//
//        });

    }

    @Test
    void testApprove2() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.WAITING.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        var result = bookingService.approve(1L, 1L, false);

//        Exception exception = assertThrows(BadRequestException.class, () -> {
//
//        });

    }

    @Test
    void testApproveNotFound() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 3L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.WAITING.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            var result = bookingService.approve(1L, 1L, false);
        });

    }

    @Test
    void testApproveBadStatus() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Exception exception = assertThrows(BadRequestException.class, () -> {
            var result = bookingService.approve(1L, 1L, false);
        });

    }

    @Test
    void testFindAllNotFoundException() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            var result = bookingService.findAll(null, "");
        });

    }

    @Test
    void testFindAllAll() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));

        when(bookingRepository.findByUserIdOrderByStartDesc(any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        var result = bookingService.findAll(1L, "ALL");
        result = bookingService.findAll(1L, "FUTURE");
        result = bookingService.findAll(1L, "CURRENT");
        result = bookingService.findAll(1L, "PAST");
        result = bookingService.findAll(1L, "WAITING");
        result = bookingService.findAll(1L, "REJECTED");


        Exception exception = assertThrows(UnsupportedStatusException.class, () -> {
            var result2 = bookingService.findAll(1L, "bad");
        });


    }

    @Test
    void testFindAllBadIdOrStatus() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));

        when(bookingRepository.findByUserIdOrderByStartDesc(any())).thenReturn(List.of(booking));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            var result = bookingService.findAll(null, "ALL");
        });


        var result2 = bookingService.findAll(1L, null);
//        result = bookingService.findAll(1L, "CURRENT");
//        result = bookingService.findAll(1L, "PAST");
//        result = bookingService.findAll(1L, "WAITING");
//        result = bookingService.findAll(1L, "REJECTED");
//
//
//        Exception exception = assertThrows(UnsupportedStatusException.class, () -> {
//            var result2 = bookingService.findAll(1L, "bad");
//        });


    }

    @Test
    void testFindAllAllPagination() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));

        when(bookingRepository.findByUserIdOrderByStartDesc(any())).thenReturn(List.of(booking));


        int fromPage = 0;
        int size = 10;
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size);

        var test = new PageImpl<Booking>(List.of(booking), pageable, List.of(booking).size());

        when(bookingRepository.findByUserIdOrderByStartDesc(any(), any())).thenReturn(test);
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        var result = bookingService.findAll(1L, "ALL", 0L, 10L);
        result = bookingService.findAll(1L, "FUTURE", 0L, 10L);
        result = bookingService.findAll(1L, "CURRENT", 0L, 10L);
        result = bookingService.findAll(1L, "PAST", 0L, 10L);
        result = bookingService.findAll(1L, "WAITING", 0L, 10L);
        result = bookingService.findAll(1L, "REJECTED", 0L, 10L);



        Exception exception = assertThrows(UnsupportedStatusException.class, () -> {
            var result2 = bookingService.findAll(1L, "bad", 0L, 10L);
        });


    }

    @Test
    void testFindAllAllByOwner() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));

        when(bookingRepository.findByUserIdOrderByStartDesc(any())).thenReturn(List.of(booking));


        int fromPage = 0;
        int size = 10;
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size);

        var test = new PageImpl<Booking>(List.of(booking), pageable, List.of(booking).size());

        when(bookingRepository.findByItemIdInOrderByStartDesc(any(), any())).thenReturn(test);
        when(bookingRepository.findByItemIdInOrderByStartDesc(any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        var result = bookingService.findAllByOwner(1L, "ALL", 0L, 10L);
        result = bookingService.findAllByOwner(1L, "FUTURE", 0L, 10L);
        result = bookingService.findAllByOwner(1L, "CURRENT", 0L, 10L);
        result = bookingService.findAllByOwner(1L, "PAST", 0L, 10L);
        result = bookingService.findAllByOwner(1L, "WAITING", 0L, 10L);
        result = bookingService.findAllByOwner(1L, "REJECTED", 0L, 10L);



        Exception exception = assertThrows(UnsupportedStatusException.class, () -> {
            var result2 = bookingService.findAllByOwner(1L, "bad", 0L, 10L);
        });

        result = bookingService.findAllByOwner(1L, "ALL");
        result = bookingService.findAllByOwner(1L, "FUTURE");
        result = bookingService.findAllByOwner(1L, "CURRENT");
        result = bookingService.findAllByOwner(1L, "PAST");
        result = bookingService.findAllByOwner(1L, "WAITING");
        result = bookingService.findAllByOwner(1L, "REJECTED");


        Exception exception2 = assertThrows(UnsupportedStatusException.class, () -> {
            var result2 = bookingService.findAllByOwner(1L, "bad");
        });


    }

    @Test
    void testFindAllAllPagination2() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));

        when(bookingRepository.findByUserIdOrderByStartDesc(any())).thenReturn(List.of(booking));


        int fromPage = 0;
        int size = 10;
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size);

        var test = new PageImpl<Booking>(List.of(booking), pageable, List.of(booking).size());

        when(bookingRepository.findByUserIdOrderByStartDesc(any(), any())).thenReturn(test);
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));


        Exception exception = assertThrows(BadRequestException.class, () -> {
            var result = bookingService.findAll(user.getId(), "ALL", -10L, 10L);
        });


        var result2 = bookingService.findAll(1L, null, null, null);


        Exception exception2 = assertThrows(BadRequestException.class, () -> {
            var result3 = bookingService.findAll(null, "ALL", -10L, 10L);
        });

        Exception exception3 = assertThrows(NotFoundException.class, () -> {
            var result4 = bookingService.findAll(null, "ALL", 0L, 10L);
        });


        var result5 = bookingService.findAll(user.getId(), null, 0L, 10L);


    }

    @Test
    void testFindAllByOwner2() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, 1L, null, "test", "test", false, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", false, null);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setItemId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));
        booking.setStatus(Status.REJECTED.toString());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setStart(LocalDateTime.parse("2023-08-04T10:11:30"));
        bookingDto.setEnd(LocalDateTime.parse("2023-08-05T10:11:30"));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));

        when(bookingRepository.findByUserIdOrderByStartDesc(any())).thenReturn(List.of(booking));


        int fromPage = 0;
        int size = 10;
//        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        Pageable pageable = PageRequest.of(fromPage, size);

        var test = new PageImpl<Booking>(List.of(booking), pageable, List.of(booking).size());

        when(bookingRepository.findByItemIdInOrderByStartDesc(any(), any())).thenReturn(test);
        when(bookingRepository.findByItemIdInOrderByStartDesc(any())).thenReturn(List.of(booking));

        when(bookingRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        var result = bookingService.findAllByOwner(1L, "ALL", null, null);
        var result2 = bookingService.findAllByOwner(1L, null, 0L, 10L);
        var result6 = bookingService.findAllByOwner(1L, null, null, null);

       Exception exception = assertThrows(BadRequestException.class, () -> {
           var result3 = bookingService.findAllByOwner(1L, "ALL", -10L, 10L);
       });

        Exception exception2 = assertThrows(NotFoundException.class, () -> {
            var result4 = bookingService.findAllByOwner(null, "ALL", 0L, 10L);
        });

        Exception exception3 = assertThrows(NotFoundException.class, () -> {
            var result5 = bookingService.findAllByOwner(null, "ALL", null, null);
        });

    }

}
