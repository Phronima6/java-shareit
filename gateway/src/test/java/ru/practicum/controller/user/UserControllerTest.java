package ru.practicum.controller.user;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.user.controller.UserClient;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserControllerTest {

    @Mock
    UserClient userClient;
    @InjectMocks
    UserController userController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    static final String PATH_USERS = "/users";
    static final String PATH_USER_ID = "user-id";

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
        when(userClient.createUser(any(UserDto.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(userDto));
        mockMvc.perform(post(PATH_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userClient.getUser(anyLong())).thenReturn(ResponseEntity.ok(userDto));
        mockMvc.perform(get(PATH_USERS + "/{" + PATH_USER_ID + "}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userClient.updateUser(anyLong(), any(UserDto.class))).thenReturn(ResponseEntity.ok(userDto));
        mockMvc.perform(patch(PATH_USERS + "/{" + PATH_USER_ID + "}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

}