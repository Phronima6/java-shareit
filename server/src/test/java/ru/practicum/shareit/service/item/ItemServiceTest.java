package ru.practicum.shareit.service.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.exception.DataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImplements;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ItemMapper itemMapper;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ItemServiceImplements itemService;

    @Test
    void createCommentUserNotBookedItem() {
        Long userId = 1L;
        Long itemId = 1L;
        CommentDto commentDto = new CommentDto();
        when(bookingRepository.findByBookerIdAndItemId(userId, itemId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> {itemService.createComment(userId, itemId, commentDto);});
        assertEquals("Ошибка при создании отзыва. Пользователь не бронировал эту вещь.",
                exception.getMessage());
    }

    @Test
    void createCommentBookingNotEnded() {
        Long userId = 1L;
        Long itemId = 1L;
        CommentDto commentDto = new CommentDto();
        Booking booking = new Booking();
        booking.setEnd(LocalDateTime.now().plusDays(1));
        when(bookingRepository.findByBookerIdAndItemId(userId, itemId)).thenReturn(Optional.of(booking));
        DataException exception = assertThrows(DataException.class,
                () -> {itemService.createComment(userId, itemId, commentDto);});
        assertEquals("Ошибка при создании отзыва. Бронирование ещё не завершено.", exception.getMessage());
    }

    @Test
    void createItemUserNotFound() {
        Long userId = 1L;
        ItemDto itemDto = new ItemDto();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> {itemService.createItem(userId, itemDto);});
        assertEquals("Ошибка при создании вещи. Пользователя с id = " + userId + " нет.",
                exception.getMessage());
    }

    @Test
    void createItem_ItemRequestNotFound_ThrowsNotFoundException() {
        Long userId = 1L;
        ItemDto itemDto = new ItemDto();
        itemDto.setRequestId(1L);
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestRepository.findById(itemDto.getRequestId())).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> {itemService.createItem(userId, itemDto);});
        assertEquals("Запроса с id = " + itemDto.getRequestId() + " нет.", exception.getMessage());
    }

    @Test
    void updateItemItemNotOwnedByUser() {
        Long userId = 1L;
        Long itemId = 1L;
        ItemDto itemDto = new ItemDto();
        Item itemOld = new Item();
        itemOld.setOwner(new User());
        itemOld.getOwner().setId(2L);
        when(itemRepository.getById(itemId)).thenReturn(itemOld);
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> {itemService.updateItem(userId, itemId, itemDto);});
        assertEquals("Ошибка, эта вещь не принадлежит пользователю.", exception.getMessage());
    }

    @Test
    void getItemReturnsItemDto() {
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item();
        ItemDto itemDto = new ItemDto();
        itemDto.setId(itemId);
        when(itemRepository.getById(itemId)).thenReturn(item);
        when(itemMapper.toItemDto(item)).thenReturn(itemDto);
        ItemDto result = itemService.getItem(userId, itemId);
        assertNotNull(result);
        assertEquals(itemId, result.getId());
    }

}