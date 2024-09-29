package ru.practicum.shareit.controller.user;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserControllerTest {

    @Mock
    UserService userService;
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
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(post(PATH_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());
        mockMvc.perform(delete(PATH_USERS + "/{" + PATH_USER_ID + "}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.getUser(anyLong())).thenReturn(userDto);
        mockMvc.perform(get(PATH_USERS + "/{" + PATH_USER_ID + "}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(patch(PATH_USERS + "/{" + PATH_USER_ID + "}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

}