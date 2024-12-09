package ru.practicum.controller.booking;

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
import ru.practicum.shareit.booking.controller.BookingClient;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingControllerTest {

    @Mock
    BookingClient bookingClient;
    @InjectMocks
    BookingController bookingController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    static final String PATH_BOOKINGS = "/bookings";
    static final String PATH_BOOKING_ID = "booking-id";
    static final String PATH_OWNER = "/owner";
    static final String X_HEADER = "X-Sharer-User-Id";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void confirmationBooking() throws Exception {
        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        when(bookingClient.confirmationBooking(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(ResponseEntity.ok(bookingDtoOutput));
        mockMvc.perform(patch(PATH_BOOKINGS + "/{" + PATH_BOOKING_ID + "}", 1)
                        .header(X_HEADER, 1)
                        .param("approved", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void createBooking() throws Exception {
        BookingDtoInput bookingDtoInput = new BookingDtoInput();
        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        when(bookingClient.createBooking(anyLong(), any(BookingDtoInput.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(bookingDtoOutput));
        mockMvc.perform(post(PATH_BOOKINGS)
                        .header(X_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDtoInput)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllBookingsFromOwner() throws Exception {
        when(bookingClient.getAllBookingsFromOwner(anyLong(),
                anyString())).thenReturn(ResponseEntity.ok(Collections.emptyList()));
        mockMvc.perform(get(PATH_BOOKINGS + PATH_OWNER)
                        .header(X_HEADER, 1)
                        .param("state", "ALL"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBookingsFromUser() throws Exception {
        when(bookingClient.getAllBookingsFromUser(anyLong(),
                anyString())).thenReturn(ResponseEntity.ok(Collections.emptyList()));
        mockMvc.perform(get(PATH_BOOKINGS)
                        .header(X_HEADER, 1)
                        .param("state", "ALL"))
                .andExpect(status().isOk());
    }

    @Test
    void getBooking() throws Exception {
        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        when(bookingClient.getBooking(anyLong(), anyLong())).thenReturn(ResponseEntity.ok(bookingDtoOutput));
        mockMvc.perform(get(PATH_BOOKINGS + "/{" + PATH_BOOKING_ID + "}", 1)
                        .header(X_HEADER, 1))
                .andExpect(status().isOk());
    }

}