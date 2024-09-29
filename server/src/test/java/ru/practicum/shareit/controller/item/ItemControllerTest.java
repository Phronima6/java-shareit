package ru.practicum.shareit.controller.item;

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
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemControllerTest {

    @Mock
    ItemService itemService;
    @InjectMocks
    ItemController itemController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    static final String PATH_ITEM = "/items";
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
        when(itemService.createItem(anyLong(), any(ItemDto.class))).thenReturn(itemDto);
        mockMvc.perform(post(PATH_ITEM)
                        .header(X_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllItemsFromUser() throws Exception {
        when(itemService.getAllItemsFromUser(anyLong())).thenReturn(Collections.emptyList());
        mockMvc.perform(get(PATH_ITEM)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

    @Test
    void getItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        when(itemService.getItem(anyLong(), anyLong())).thenReturn(itemDto);
        mockMvc.perform(get(PATH_ITEM + "/{" + PATH_ITEM_ID + "}", 1)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

    @Test
    void updateItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        when(itemService.updateItem(anyLong(), anyLong(), any(ItemDto.class))).thenReturn(itemDto);
        mockMvc.perform(patch(PATH_ITEM + "/{" + PATH_ITEM_ID + "}", 1)
                        .header(X_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk());
    }

    @Test
    void searchItem() throws Exception {
        when(itemService.searchItem(anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get(PATH_ITEM + PATH_SEARCH)
                        .param("text", "test"))
                .andExpect(status().isOk());
    }

}