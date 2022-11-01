package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
public class BookingControllerTest {

    @MockBean
    public BookingServiceImpl bookingService;

    @Autowired
    public MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testAdd() throws Exception {
        //Assign
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

        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("test");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        when(bookingService.add(any(), any())).thenReturn(booking);

        //Act
        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

    }
}
