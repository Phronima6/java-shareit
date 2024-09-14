package ru.practicum.shareit.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.exception.DataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ItemServiceImplements implements ItemService {

    BookingMapper bookingMapper;
    BookingRepository bookingRepository;
    CommentMapper commentMapper;
    CommentRepository commentRepository;
    ItemMapper itemMapper;
    ItemRepository itemRepository;
    UserRepository userRepository;

    @Override
    public CommentDto createComment(final Long userId, final Long itemId, final CommentDto commentDto) {
        Booking booking = bookingRepository.findByBookerIdAndItemId(userId, itemId)
                .orElseThrow(() -> new NotFoundException(STR
                        ."Ошибка при создании отзыва. Пользователь id\{userId} не бронировал вещь id\{itemId}."));
        if (booking.getEnd().isAfter(LocalDateTime.now())) {
            log.info("ItemServiceImplements, createComment, endDataIsAfter.");
            throw new DataException("Ошибка при создании отзыва. Бронирование ещё не завершено.");
        }
        Comment comment = commentMapper.toComment(commentDto);
        comment.setAuthor(userRepository.getById(userId));
        comment.setCreated(LocalDateTime.now());
        comment.setItem(itemRepository.getById(itemId));
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public ItemDto createItem(final Long userId, final ItemDto itemDto) {
        if (!userRepository.existsById(userId)) {
            log.warn(STR."ItemServiceImplements, createItem, \{userId}.");
            throw new NotFoundException(STR."Ошибка при добавлении вещи. Пользователя id\{userId} нет.");
        }
        final Item item = itemMapper.toItem(itemDto);
        item.setOwner(userRepository.getById(userId));
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> getAllItemsFromUser(final Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(itemMapper::toItemDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItem(final Long userId, final Long itemId) {
        final ItemDto itemDto = itemMapper.toItemDto(itemRepository.getById(itemId));
        LocalDateTime now = LocalDateTime.now();
        itemDto.setComments(commentRepository.findAllByItemId(itemId).stream()
                .map(commentMapper::toCommentDto)
                .toList());
        itemDto.setLastBooking(bookingRepository.findLastBooking(itemId, now)
                .map(bookingMapper::toBookingDtoOutput)
                .orElse(null));
        if (itemDto.getLastBooking() != null && itemDto.getLastBooking().getItem().getLastBooking() == null) {
            itemDto.setLastBooking(null);
        }
        itemDto.setNextBooking(bookingRepository.findNextBooking(itemId, now)
                .map(bookingMapper::toBookingDtoOutput)
                .orElse(null));
        if (itemDto.getNextBooking() != null && itemDto.getNextBooking().getItem().getNextBooking() == null) {
            itemDto.setNextBooking(null);
        }
        return itemDto;
    }

    @Override
    public ItemDto updateItem(final Long userId,  final Long itemId, final ItemDto itemDto) {
        final Item item = itemMapper.toItem(itemDto);
        final Item itemOld = itemRepository.getById(itemId);
        if (!itemOld.getOwner().getId().equals(userId)) {
            log.warn("ItemServiceImplements, updateItem.");
            throw new NotFoundException(STR."Ошибка, эта вещь не принадлежит пользователю id\{userId}.");
        }
        if (item.getAvailable() == null) {
            log.info("ItemServiceImplements, updateItem, availableIsEmpty.");
            item.setAvailable(itemOld.getAvailable());
        }
        if (item.getDescription() == null) {
            log.info("ItemServiceImplements, updateItem, descriptionIsEmpty.");
            item.setDescription(itemOld.getDescription());
        }
        if (item.getName() == null) {
            log.info("ItemServiceImplements, updateItem, nameIsEmpty.");
            item.setName(itemOld.getName());
        }
        item.setOwner(userRepository.getById(userId));
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> searchItem(final String text) {
        if (text.isEmpty()) {
            log.info("ItemServiceImplements, searchItem, textIsEmpty.");
            return new ArrayList<>();
        }
        return itemRepository.searchItem(text.toLowerCase().trim()).stream()
                .map(itemMapper::toItemDto)
                .toList();
    }

}