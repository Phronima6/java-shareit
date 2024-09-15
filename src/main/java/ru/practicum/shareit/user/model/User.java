package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "users")
public class User {

    @Column(nullable = false)
    String email; // Электронная почта пользователя
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id; // Идентификатор пользователя
    @Column(nullable = false)
    String name; // Имя пользователя

}