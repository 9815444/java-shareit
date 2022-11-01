package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserValidation;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class ItemServiceImplTest {

    private EntityManager em;

    private ItemServiceImpl itemService;

    private ItemRepository itemRepository;

    private UserRepository userRepository;

    private UserValidation userValidation;

    private CommentRepository commentRepository;

    private BookingRepository bookingRepository;

    @Autowired
    private ItemValidation itemValidation;

    private RequestRepository requestRepository;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        when(itemRepository.save(any())).then(invocation -> invocation.getArgument(0));

        userRepository = mock(UserRepository.class);
        commentRepository = mock(CommentRepository.class);
        bookingRepository = mock(BookingRepository.class);
        itemValidation = mock(ItemValidation.class);
        requestRepository = mock(RequestRepository.class);
        userValidation = mock(UserValidation.class);

        itemService = new ItemServiceImpl(itemRepository, userRepository, commentRepository,
                bookingRepository, itemValidation, requestRepository);
    }

    @Test
    void testAdd() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        var result = itemService.add(1L, itemDto);

        assertEquals(itemDto.getName(), result.getName());

        ItemDto itemDto3 = new ItemDto(1L, "test", "test", true, 1L);
        var result2 = itemService.add(1L, itemDto3);
        assertEquals(itemDto3.getName(), result2.getName());

    }

    @Test
    void testUpdate() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        var result = itemService.update(1L, 1L, itemDto);

        assertEquals(itemDto.getName(), result.getName());

    }

    @Test
    void testDelete() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        itemService.delete(1L, 1L);

    }

    @Test
    void testFind() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        var result = itemService.find(1L);

        assertEquals(item.getName(), result.getName());

    }

    @Test
    void testFindByUser() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(commentRepository.findByItemId(any())).thenReturn(new ArrayList<>());
        var result = itemService.findByUser(1L, 1L);

        assertEquals(item.getName(), result.getName());

    }

    @Test
    void testFindLast() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

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

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(any(), any()))
                .thenReturn(List.of(booking));

        var result = itemService.findLast(1L, true);

        assertEquals(bookingDto.getBookerId(), result.getBookerId());

    }

    @Test
    void testFindNext() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

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

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(bookingRepository.findByItemIdAndStartAfterOrderByStart(any(), any()))
                .thenReturn(List.of(booking));

        var result = itemService.findNext(1L, true);

        assertEquals(bookingDto.getBookerId(), result.getBookerId());

    }

    @Test
    void testFindUserItems() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

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

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository.findByUserIdOrderById(any())).thenReturn(List.of(item));
        when(commentRepository.findByItemId(any())).thenReturn(new ArrayList<>());

        var result = itemService.findUserItems(1L);

        assertEquals(item.getName(), result.get(0).getName());

    }

    @Test
    void testFindItemsByText() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

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

        when(requestRepository.findById(any())).thenReturn(Optional.of(request));
        when(itemRepository
                .findByAvailableAndDescriptionContainingIgnoreCaseOrAvailableAndNameContainingIgnoreCase(any(), any(), any(), any())).thenReturn(List.of(item));
        when(commentRepository.findByItemId(any())).thenReturn(new ArrayList<>());

        var result = itemService.findItemsByText("");

        var result2 = itemService.findItemsByText("111");

    }

    @Test
    void testAddComment() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUserId(1L);
        comment.setItemId(1L);
        comment.setText("text");
        comment.setCreated(null);
        comment.setAuthorName("authorname");

        CommentDto commentDto = new CommentDto();
        commentDto.setText("text");
        commentDto.setCreated(null);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(commentRepository.save(any())).thenReturn(comment);
        when(commentRepository.findByItemId(any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findByUserIdAndItemIdAndStatusAndEndBefore(any(), any(), any(), any()))
                .thenReturn(List.of(booking));

        var result = itemService.addComment(1L, 1L, commentDto);


    }

    @Test
    void testAddCommentBad() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUserId(1L);
        comment.setItemId(1L);
        comment.setText("text");
        comment.setCreated(null);
        comment.setAuthorName("authorname");

        CommentDto commentDto = new CommentDto();
        commentDto.setText("");
        commentDto.setCreated(null);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(commentRepository.save(any())).thenReturn(comment);
        when(commentRepository.findByItemId(any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findByUserIdAndItemIdAndStatusAndEndBefore(any(), any(), any(), any()))
                .thenReturn(List.of(booking));

        Exception exception = assertThrows(BadRequestException.class, () -> {
            itemService.addComment(1L, 1L, commentDto);
        });


    }

    @Test
    void testAddCommentEmptyBookings() {

        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

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

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUserId(1L);
        comment.setItemId(1L);
        comment.setText("text");
        comment.setCreated(null);
        comment.setAuthorName("authorname");

        CommentDto commentDto = new CommentDto();
        commentDto.setText("test");
        commentDto.setCreated(null);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(commentRepository.save(any())).thenReturn(comment);
        when(commentRepository.findByItemId(any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findByUserIdAndItemIdAndStatusAndEndBefore(any(), any(), any(), any()))
                .thenReturn(new ArrayList<>());

        Exception exception = assertThrows(BadRequestException.class, () -> {
            itemService.addComment(1L, 1L, commentDto);
        });


    }
}
