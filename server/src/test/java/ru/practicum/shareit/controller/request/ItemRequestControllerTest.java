package ru.practicum.shareit.controller.request;

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
import ru.practicum.shareit.request.controller.ItemRequestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestControllerTest {

    @Mock
    ItemRequestService itemRequestService;
    @InjectMocks
    ItemRequestController itemRequestController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    static final String PATH_ALL = "/all";
    static final String PATH_REQUEST_ID = "request-id";
    static final String PATH_REQUESTS = "/requests";
    static final String X_HEADER = "X-Sharer-User-Id";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemRequestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createItemRequest() throws Exception {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("Sample description");
        when(itemRequestService.createItemRequest(anyLong(), any(ItemRequestDto.class)))
                .thenReturn(itemRequestDto);
        mockMvc.perform(post(PATH_REQUESTS)
                        .header(X_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllFromRequestor() throws Exception {
        when(itemRequestService.getAllFromRequestor(anyLong()))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get(PATH_REQUESTS)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

    @Test
    void getItemRequest() throws Exception {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        when(itemRequestService.getItemRequest(anyLong(), anyLong()))
                .thenReturn(itemRequestDto);
        mockMvc.perform(get(PATH_REQUESTS + "/{" + PATH_REQUEST_ID + "}", 1)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

    @Test
    void getOtherItemRequests() throws Exception {
        when(itemRequestService.getOtherItemRequests(anyLong()))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get(PATH_REQUESTS + PATH_ALL)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

}