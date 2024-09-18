package ru.practicum.shareit.booking;

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
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.user.model.TypeUser;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;
    @InjectMocks
    private BookingController bookingController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void confirmationBooking() throws Exception {
        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        when(bookingService.confirmationBooking(anyLong(), anyLong(), anyBoolean())).thenReturn(bookingDtoOutput);

        mockMvc.perform(patch("/bookings/{bookingId}", 1)
                        .header("X-Sharer-User-Id", 1)
                        .param("approved", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void createBooking() throws Exception {
        BookingDtoInput bookingDtoInput = new BookingDtoInput();
        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        when(bookingService.createBooking(anyLong(), any(BookingDtoInput.class))).thenReturn(bookingDtoOutput);

        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDtoInput)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllBookingsFromOwner() throws Exception {
        when(bookingService.getAllBookingsFromUser(any(TypeUser.class), anyLong(), any(State.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .param("state", "ALL"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBookingsFromUser() throws Exception {
        when(bookingService.getAllBookingsFromUser(any(TypeUser.class), anyLong(), any(State.class)))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .param("state", "ALL"))
                .andExpect(status().isOk());
    }

    @Test
    void getBooking() throws Exception {
        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        when(bookingService.getBooking(anyLong(), anyLong())).thenReturn(bookingDtoOutput);
        mockMvc.perform(get("/bookings/{bookingId}", 1)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

}