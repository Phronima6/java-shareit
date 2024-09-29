package ru.practicum.shareit.booking.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingDtoInput;
import ru.practicum.shareit.client.BaseClient;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class BookingClient extends BaseClient {

    static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> confirmationBooking(final Long userId, final Long bookingId, final boolean approved) {
        final Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, null);
    }

    public ResponseEntity<Object> createBooking(final Long userId, final BookingDtoInput bookingDtoInput) {
        return post("", userId, bookingDtoInput);
    }

    public ResponseEntity<Object> getAllBookingsFromOwner(final Long userId, final String state) {
        final Map<String, Object> parameters = Map.of("state", state);
        return get("/owner?state={state}", userId, parameters);
    }

    public ResponseEntity<Object> getAllBookingsFromUser(final Long userId, final String state) {
        final Map<String, Object> parameters = Map.of("state", state);
        return get("?state={state}", userId, parameters);
    }

    public ResponseEntity<Object> getBooking(final Long userId, final Long bookingId) {
        return get("/" + bookingId, userId);
    }

}