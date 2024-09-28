package ru.practicum.shareit.booking.model;

public enum Status {

    APPROVED, // Бронирование подтверждено владельцем
    CANCELED, // Бронирование отменено заказчиком
    REJECTED, // Бронирование отклонено владельцем
    WAITING //  Бронирование, ожидает одобрения владельца

}