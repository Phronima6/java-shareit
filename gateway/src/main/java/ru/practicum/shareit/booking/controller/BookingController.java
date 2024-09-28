package ru.practicum.shareit.booking.controller;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoInput;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    BookingClient bookingClient;
    static final String xHeader = "X-Sharer-User-Id";

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> confirmationBooking(@RequestHeader(xHeader) @Positive final Long userId,
                                                      @PathVariable final @Positive Long bookingId,
                                                      @RequestParam final Boolean approved) {
        return bookingClient.confirmationBooking(userId, bookingId, approved);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createBooking(@RequestHeader(xHeader) @Positive final Long userId,
                                                @RequestBody final BookingDtoInput bookingDtoInput) {
        return bookingClient.createBooking(userId, bookingDtoInput);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsFromOwner(@RequestHeader(xHeader) @Positive final Long userId,
                                                          @RequestParam (defaultValue = "ALL") final String state) {
        return bookingClient.getAllBookingsFromOwner(userId, state);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsFromUser(@RequestHeader(xHeader) @Positive final Long userId,
                                                         @RequestParam (defaultValue = "ALL") final String state) {
        return bookingClient.getAllBookingsFromUser(userId, state);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader(xHeader) @Positive final Long userId,
                                             @PathVariable @Positive final Long bookingId) {
        return bookingClient.getBooking(userId, bookingId);
    }

}