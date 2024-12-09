package ru.practicum.shareit.service.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingServiceImplements;
import ru.practicum.shareit.exception.DataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.TypeUser;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private BookingServiceImplements bookingService;

    @Test
    void confirmationBookingNotOwner() {
        Long userId = 1L;
        Long bookingId = 1L;
        Boolean approved = true;
        Booking booking = new Booking();
        Item item = new Item();
        item.setId(1L);
        booking.setItem(item);
        item.setOwner(new User());
        item.getOwner().setId(2L);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(itemRepository.getById(booking.getItem().getId())).thenReturn(item);
        DataException exception = assertThrows(DataException.class,
                () -> bookingService.confirmationBooking(userId, bookingId, approved));
        assertEquals("Ошибка при подтверждении бронирования. Пользователь не владелец вещи.",
                exception.getMessage());
    }

    @Test
    void createBookingItemNotFound() {
        Long userId = 1L;
        BookingDtoInput bookingDtoInput = new BookingDtoInput();
        bookingDtoInput.setItemId(1L);
        Booking booking = new Booking();
        booking.setItemId(1L);
        when(bookingMapper.toBooking(bookingDtoInput)).thenReturn(booking);
        when(itemRepository.findById(bookingDtoInput.getItemId())).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookingService.createBooking(userId, bookingDtoInput));
        assertEquals("Ошибка при бронировании. Такой вещи нет.", exception.getMessage());
    }

    @Test
    void createBookingItemNotAvailable() {
        Long userId = 1L;
        BookingDtoInput bookingDtoInput = new BookingDtoInput();
        bookingDtoInput.setItemId(1L);
        Booking booking = new Booking();
        booking.setItemId(1L);
        Item item = new Item();
        item.setAvailable(false);
        when(bookingMapper.toBooking(bookingDtoInput)).thenReturn(booking);
        when(itemRepository.findById(bookingDtoInput.getItemId())).thenReturn(Optional.of(item));
        DataException exception = assertThrows(DataException.class,
                () -> bookingService.createBooking(userId, bookingDtoInput));
        assertEquals("Ошибка при бронировании. Вещи недоступна.", exception.getMessage());
    }

    @Test
    void getAllBookingsFromUserUserNotFound() {
        Long userId = 1L;
        TypeUser typeUser = TypeUser.USER;
        State state = State.ALL;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookingService.getAllBookingsFromUser(typeUser, userId, state));
        assertEquals("Ошибка при получении информации о бронированиях. Пользователя с id = "
                + userId + " нет.", exception.getMessage());
    }

    @Test
    void getBookingNotOwnerOrBooker() {
        Long userId = 1L;
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBooker(new User());
        booking.getBooker().setId(2L);
        booking.setItem(new Item());
        booking.getItem().setOwner(new User());
        booking.getItem().getOwner().setId(3L);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        DataException exception = assertThrows(DataException.class,
                () -> bookingService.getBooking(userId, bookingId));
        assertEquals("Ошибка при получении информации о бронировании."
                + " Информация может быть предоставлена только владельцу или создателю запроса на бронирование",
                exception.getMessage());
    }

}