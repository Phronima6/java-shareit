package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

@Component
public class BookingMapper {

    public BookingDto toBookingDto(final Booking booking) {
        final BookingDto bookingDto = new BookingDto();
        bookingDto.setBooker(booking.getBooker());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setId(booking.getId());
        bookingDto.setItem(booking.getItem());
        bookingDto.setStart(booking.getStart());
        bookingDto.setStatus(booking.getStatus());
        return bookingDto;
    }

    public Booking toBooking(final BookingDto bookingDto) {
        final Booking booking = new Booking();
        booking.setBooker(bookingDto.getBooker());
        booking.setEnd(bookingDto.getEnd());
        booking.setId(bookingDto.getId());
        booking.setItem(bookingDto.getItem());
        booking.setStart(bookingDto.getStart());
        booking.setStatus(bookingDto.getStatus());
        return booking;
    }

}