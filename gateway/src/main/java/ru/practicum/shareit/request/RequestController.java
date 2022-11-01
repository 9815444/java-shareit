package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody RequestDto requestDto) {
        return requestClient.addRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> findRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.findRequests(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        return requestClient.findRequest(userId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Long from,
                                                  @Positive @RequestParam(name = "size", defaultValue = "10") Long size) {
        return requestClient.findAllRequests(userId, from, size);
    }
}
