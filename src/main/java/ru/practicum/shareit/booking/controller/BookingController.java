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
    String xHeader = "X-Sharer-User-Id";

    @PatchMapping("/{bookingId}")
    public BookingDtoOutput confirmationBooking(@RequestHeader(xHeader) final Long userId,
                                               @PathVariable final Long bookingId,
                                               @RequestParam final Boolean approved) {
        return bookingService.confirmationBooking(userId, bookingId, approved);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoOutput createBooking(@RequestHeader(xHeader) final Long userId,
                                          @RequestBody final BookingDtoInput bookingDtoInput) {
        return bookingService.createBooking(userId, bookingDtoInput);
    }

    @GetMapping("/owner")
    public Collection<BookingDtoOutput> getAllBookingsFromOwner(@RequestHeader(xHeader) final Long userId,
                                                                @RequestParam (defaultValue = "ALL") final State state) {
        return bookingService.getAllBookingsFromUser(TypeUser.OWNER, userId, state);
    }

    @GetMapping
    public Collection<BookingDtoOutput> getAllBookingsFromUser(@RequestHeader(xHeader) final Long userId,
                                                               @RequestParam (defaultValue = "ALL") final State state) {
        return bookingService.getAllBookingsFromUser(TypeUser.USER, userId, state);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoOutput getBooking(@RequestHeader(xHeader) final Long userId,
                                       @PathVariable final Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

}