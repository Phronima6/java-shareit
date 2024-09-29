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
    static final String PATH_BOOKING_ID = "booking-id";
    static final String PATH_OWNER = "/owner";
    static final String X_HEADER = "X-Sharer-User-Id";

    @PatchMapping("/{" + PATH_BOOKING_ID + "}")
    public ResponseEntity<Object> confirmationBooking(@RequestHeader(X_HEADER) @Positive final Long userId,
                                                      @PathVariable(PATH_BOOKING_ID) final @Positive Long bookingId,
                                                      @RequestParam final Boolean approved) {
        return bookingClient.confirmationBooking(userId, bookingId, approved);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createBooking(@RequestHeader(X_HEADER) @Positive final Long userId,
                                                @RequestBody final BookingDtoInput bookingDtoInput) {
        return bookingClient.createBooking(userId, bookingDtoInput);
    }

    @GetMapping(PATH_OWNER)
    public ResponseEntity<Object> getAllBookingsFromOwner(@RequestHeader(X_HEADER) @Positive final Long userId,
                                                          @RequestParam (defaultValue = "ALL") final String state) {
        return bookingClient.getAllBookingsFromOwner(userId, state);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsFromUser(@RequestHeader(X_HEADER) @Positive final Long userId,
                                                         @RequestParam (defaultValue = "ALL") final String state) {
        return bookingClient.getAllBookingsFromUser(userId, state);
    }

    @GetMapping("/{" + PATH_BOOKING_ID + "}")
    public ResponseEntity<Object> getBooking(@RequestHeader(X_HEADER) @Positive final Long userId,
                                             @PathVariable(PATH_BOOKING_ID) @Positive final Long bookingId) {
        return bookingClient.getBooking(userId, bookingId);
    }

}