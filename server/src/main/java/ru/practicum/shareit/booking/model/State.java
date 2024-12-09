package ru.practicum.shareit.booking.model;

public enum State {

    ALL, // Все бронирования
    CURRENT, // Текущие бронирования
    FUTURE, // Будущие бронирования
    PAST, // Завершенные бронирования
    REJECTED, // Отклонённые бронирования
    WAITING // Бронирования ожидающие подтверждения

}