package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemController itemController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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
        when(itemService.createItem(anyInt(), any(ItemDto.class))).thenReturn(itemDto);
        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllItemsFromUser() throws Exception {
        when(itemService.getAllItemsFromUser(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    void getItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        when(itemService.getItem(anyInt())).thenReturn(itemDto);
        mockMvc.perform(get("/items/{itemId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void updateItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        when(itemService.updateItem(anyInt(), anyInt(), any(ItemDto.class))).thenReturn(itemDto);
        mockMvc.perform(patch("/items/{itemId}", 1)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk());
    }

    @Test
    void searchItem() throws Exception {
        when(itemService.searchItem(anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/items/search")
                        .param("text", "test"))
                .andExpect(status().isOk());
    }

}