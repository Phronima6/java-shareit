package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.user.model.TypeUser;
import java.util.Collection;

public interface BookingService {

    // Подтверждение или отклонение запроса на бронирование
    BookingDtoOutput confirmationBooking(final Long userId, final Long bookingId, final Boolean approved);

    // Добавление нового запроса на бронирование
    BookingDtoOutput createBooking(final Long userId, final BookingDtoInput bookingDtoInput);

    // Получение списка всех бронирований пользователя
    Collection<BookingDtoOutput> getAllBookingsFromUser(final TypeUser typeUser, final Long userId, final State state);

    // Получение информации по бронированию
    BookingDtoOutput getBooking(final Long userId, final Long bookingId);

}