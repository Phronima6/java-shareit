package ru.practicum.shareit.booking.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingMapper {

    public Booking toBooking(final BookingDtoInput bookingDtoInput) {
        final Booking booking = new Booking();
        booking.setEnd(bookingDtoInput.getEnd());
        booking.setItemId(bookingDtoInput.getItemId());
        booking.setStart(bookingDtoInput.getStart());
        return booking;
    }

    public BookingDtoOutput toBookingDtoOutput(final Booking booking) {
        final BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        final UserDto userDto = new UserDto();
        userDto.setId(booking.getBooker().getId());
        bookingDtoOutput.setBooker(userDto);
        bookingDtoOutput.setEnd(booking.getEnd());
        bookingDtoOutput.setId(booking.getId());
        final ItemDto itemDto = new ItemDto();
        itemDto.setId(booking.getItem().getId());
        itemDto.setName(booking.getItem().getName());
        bookingDtoOutput.setItem(itemDto);
        bookingDtoOutput.setStart(booking.getStart());
        bookingDtoOutput.setStatus(booking.getStatus());
        return bookingDtoOutput;
    }

}