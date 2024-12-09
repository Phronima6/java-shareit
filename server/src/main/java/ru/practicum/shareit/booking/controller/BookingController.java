package ru.practicum.shareit.booking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.user.model.TypeUser;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    BookingService bookingService;
    static final String PATH_BOOKING_ID = "booking-id";
    static final String PATH_OWNER = "/owner";
    static final String X_HEADER = "X-Sharer-User-Id";

    @PatchMapping("/{" + PATH_BOOKING_ID + "}")
    public BookingDtoOutput confirmationBooking(@RequestHeader(X_HEADER) final Long userId,
                                               @PathVariable(PATH_BOOKING_ID) final Long bookingId,
                                               @RequestParam final Boolean approved) {
        return bookingService.confirmationBooking(userId, bookingId, approved);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoOutput createBooking(@RequestHeader(X_HEADER) final Long userId,
                                          @RequestBody final BookingDtoInput bookingDtoInput) {
        return bookingService.createBooking(userId, bookingDtoInput);
    }

    @GetMapping(PATH_OWNER)
    public Collection<BookingDtoOutput> getAllBookingsFromOwner(@RequestHeader(X_HEADER) final Long userId,
                                                                @RequestParam (defaultValue = "ALL") final State state) {
        return bookingService.getAllBookingsFromUser(TypeUser.OWNER, userId, state);
    }

    @GetMapping
    public Collection<BookingDtoOutput> getAllBookingsFromUser(@RequestHeader(X_HEADER) final Long userId,
                                                               @RequestParam (defaultValue = "ALL") final State state) {
        return bookingService.getAllBookingsFromUser(TypeUser.USER, userId, state);
    }

    @GetMapping("/{" + PATH_BOOKING_ID + "}")
    public BookingDtoOutput getBooking(@RequestHeader(X_HEADER) final Long userId,
                                       @PathVariable(PATH_BOOKING_ID) final Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

}