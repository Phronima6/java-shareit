package ru.practicum.shareit.user;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyInt());
        mockMvc.perform(delete("/users/{userId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.getUser(anyInt())).thenReturn(userDto);
        mockMvc.perform(get("/users/{userId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.updateUser(anyInt(), any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(patch("/users/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

}