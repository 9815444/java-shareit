package ru.practicum.shareit.item;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
//@Transactional
public class ItemControllerTest {

    @MockBean
    public ItemService itemService;

    private ItemController itemController;

    private ItemRepository itemRepository;

    @Autowired
    public MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testAddItem() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());
        when(itemService.add(any(), any())).thenReturn(itemDtoFull);

        //Act
        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemDtoFull.getId()));
    }

    @Test
    void testAddComment() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

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

        when(itemService.addComment(any(), any(), any())).thenReturn(comment);

        //Act
        mockMvc.perform(post("/items/{id}/comment", 1L)
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()));
    }

    @Test
    void testUpdateItem() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

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

        when(itemService.update(any(), any(), any())).thenReturn(item);

        //Act
        mockMvc.perform(patch("/items/{id}", 1L)
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()));
    }

    @Test
    void testDeleteItem() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

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

        //Act
        mockMvc.perform(delete("/items/{id}", 1L)
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").value(item.getId()));
    }

    @Test
    void testGetItem() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

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

        when(itemService.findByUser(any(), any())).thenReturn(item);

        //Act
        mockMvc.perform(get("/items/{id}", 1L)
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()));
    }

    @Test
    void testGetUserItems() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

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

        var items = List.of(item);
        when(itemService.findUserItems(any())).thenReturn(items);

        //Act
        mockMvc.perform(get("/items", 1L)
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(item.getId()));
    }

    @Test
    void testSearching() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        Item item = new Item(1L, user.getId(), null, "test", "test", true, null, null, new ArrayList<>());
        ItemDto itemDto = new ItemDto(1L, "test", "test", true, null);
        ItemDtoFull itemDtoFull = new ItemDtoFull(1L, "test", "test", true, null, null, null, new ArrayList<>());

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

        var items = List.of(item);
        when(itemService.findItemsByText(any())).thenReturn(items);

        //Act
        mockMvc.perform(get("/items/search")
                        .param("text", "text")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(item.getId()));
    }


}
