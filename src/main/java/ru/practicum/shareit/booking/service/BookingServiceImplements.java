package ru.practicum.shareit.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.DataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.TypeUser;
import ru.practicum.shareit.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class BookingServiceImplements implements BookingService {

    BookingMapper bookingMapper;
    BookingRepository bookingRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;

    @Override
    public BookingDtoOutput confirmationBooking(final Long userId, final Long bookingId, final Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).get();
        Item item = itemRepository.getById(booking.getItem().getId());
        if (!item.getOwner().getId().equals(userId)) {
            log.info("BookingServiceImplements, confirmationBooking, NotOwner.");
            throw new DataException("Ошибка при подтверждении бронирования. Пользователь не владелец вещи.");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        item.setAvailable(false);
        itemRepository.save(item);
        return bookingMapper.toBookingDtoOutput(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoOutput createBooking(final Long userId, final BookingDtoInput bookingDtoInput) {
        Booking booking = bookingMapper.toBooking(bookingDtoInput);
        Item item = itemRepository.findById(booking.getItemId()).orElseThrow(() -> new NotFoundException("Ошибка при"
                + " бронировании. Такой вещи нет."));
        if (!item.getAvailable()) {
            log.info("BookingServiceImplements, createBooking, itemNotAvailable.");
            throw new DataException("Ошибка при бронировании. Вещи недоступна.");
        }
        booking.setItem(item);
        booking.setStatus(Status.WAITING);
        booking.setBooker(userRepository.getById(userId));
        return bookingMapper.toBookingDtoOutput(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookingDtoOutput> getAllBookingsFromUser(final TypeUser typeUser,
                                                               final Long userId, final State state) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Ошибка при получении информации "
                + " о бронированиях. Такого пользователя нет."));
        Collection<Booking> bookings = new ArrayList<>();
        if (TypeUser.OWNER.equals(typeUser)) {
            bookings = bookingRepository.findAllByItemOwnerId(userId);
        }
        if (TypeUser.USER.equals(typeUser)) {
            bookings = bookingRepository.findAllByBookerId(userId);
        }
        LocalDateTime now = LocalDateTime.now();
        bookings = bookings.stream()
                .filter(booking -> switch (state) {
                    case CURRENT -> booking.getStatus().equals(Status.APPROVED)
                            && booking.getStart().isBefore(now)
                            && booking.getEnd().isAfter(now);
                    case FUTURE -> booking.getStatus().equals(Status.APPROVED)
                            && booking.getStart().isAfter(now);
                    case PAST -> booking.getStatus().equals(Status.APPROVED)
                            && booking.getEnd().isBefore(now);
                    case REJECTED -> booking.getStatus().equals(Status.REJECTED);
                    case WAITING -> booking.getStatus().equals(Status.WAITING);
                    default -> true;
                })
                .toList();
        return bookings.stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .map(bookingMapper::toBookingDtoOutput)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDtoOutput getBooking(final Long userId, final Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            log.info("BookingServiceImplements, getBooking, NotOwnerOrBooker.");
            throw new DataException("Ошибка при получении информации о бронировании."
                    + " Информация может быть предоставлена только владельцу или создателю запроса на бронирование");
        }
        return bookingMapper.toBookingDtoOutput(booking);
    }

}