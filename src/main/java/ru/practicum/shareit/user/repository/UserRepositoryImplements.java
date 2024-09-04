package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserRepositoryImplements implements UserRepository {

    Map<Integer, User> users = new HashMap<>();

    @Override
    public User createUser(final User user) {
        user.setId(getNextId());
        log.info("UserStorageImplements, createUser, userId created = {}.", user.getId());
        users.put(user.getId(), user);
        log.info("UserStorageImplements, createUser, User created.");
        return getUser(user.getId());
    }

    @Override
    public void deleteUser(final Integer userId) {
        users.remove(userId);
        log.info("UserStorageImplements, deleteUser, User deleted.");
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUser(final Integer userId) {
        return users.get(userId);
    }

    @Override
    public User updateUser(final Integer userId, final User user) {
        users.put(userId, user);
        log.info("UserStorageImplements, updateUser, User updated.");
        return getUser(userId);
    }


    // Генератор целочисленного идентификатора пользователя
    public Integer getNextId() {
        int currentMaxId = users.keySet().stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}