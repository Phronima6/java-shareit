package ru.practicum.controller.item;

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
import ru.practicum.shareit.item.controller.ItemClient;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemControllerTest {

    @Mock
    ItemClient itemClient;
    @InjectMocks
    ItemController itemController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    static final String PATH_COMMENT = "comment";
    static final String PATH_ITEMS = "/items";
    static final String PATH_ITEM_ID = "item-id";
    static final String PATH_SEARCH = "/search";
    static final String X_HEADER = "X-Sharer-User-Id";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Sample Item");
        itemDto.setDescription("This is a sample item description.");
        itemDto.setAvailable(true);
        when(itemClient.createItem(anyLong(), any(ItemDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(itemDto));
        mockMvc.perform(post(PATH_ITEMS)
                        .header(X_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllItemsFromUser() throws Exception {
        when(itemClient.getAllItemsFromUser(anyLong())).thenReturn(ResponseEntity.ok(Collections.emptyList()));
        mockMvc.perform(get(PATH_ITEMS)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

    @Test
    void getItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        when(itemClient.getItem(anyLong(), anyLong())).thenReturn(ResponseEntity.ok(itemDto));
        mockMvc.perform(get(PATH_ITEMS + "/{" + PATH_ITEM_ID + "}", 1)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

    @Test
    void updateItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        when(itemClient.updateItem(anyLong(), anyLong(), any(ItemDto.class))).thenReturn(ResponseEntity.ok(itemDto));
        mockMvc.perform(patch(PATH_ITEMS + "/{" + PATH_ITEM_ID + "}", 1)
                        .header(X_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk());
    }

    @Test
    void searchItem() throws Exception {
        when(itemClient.searchItem(anyLong(), anyString())).thenReturn(ResponseEntity.ok(Collections.emptyList()));
        mockMvc.perform(get(PATH_ITEMS + PATH_SEARCH)
                        .header(X_HEADER, 1)
                        .param("text", "test"))
                .andExpect(status().isOk());
    }

}