package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoFull;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public Request addRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody RequestDto requestDto) {
        return requestService.addRequest(userId, requestDto);
    }

    @GetMapping
    public List<RequestDtoFull> findRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.findRequests(userId);
    }

    @GetMapping("/{id}")
    public RequestDtoFull findRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        return requestService.findRequest(userId, id);
    }

    @GetMapping("/all")
    public List<RequestDtoFull> findAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(required = false) Long from, @RequestParam(required = false) Long size) {
        return requestService.findAllRequests(userId, from, size);
    }
}
