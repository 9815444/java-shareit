package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    public UserService userService;

    private UserController controller;

    private UserRepository userRepository;

    @Autowired
    public MockMvc mockMvc;

//    @Autowired
//    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testCreateUser() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        when(userService.addUser(any())).thenReturn(user);

        //Act
        mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    void testUpdateUser() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        when(userService.updateUser(any(), any())).thenReturn(user);

        //Act
        mockMvc.perform(patch("/users/{id}", user.getId())
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()));
    }

    @Test
    void testDeleteUser() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
//        when(userService.updateUser(any(), any())).thenReturn(user);

        //Act
        mockMvc.perform(delete("/users/{id}", user.getId())
//                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.name").value(userDto.getName()));
    }

    @Test
    void testGetUser() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        when(userService.findUser(any())).thenReturn(user);

        //Act
        mockMvc.perform(get("/users/{id}", user.getId())
//                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()));
    }

    @Test
    void testGetAllUser() throws Exception {
        //Assign
        UserDto userDto = new UserDto("name", "email@email.com");
        User user = new User(Long.valueOf(1), "name", "email@email.com");
        when(userService.findAll()).thenReturn(List.of(user));

        //Act
        mockMvc.perform(get("/users")
//                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(userDto.getName()));
    }

}
