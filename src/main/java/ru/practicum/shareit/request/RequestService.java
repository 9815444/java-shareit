package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoFull;

import java.util.List;

public interface RequestService {

    Request addRequest(Long userId, RequestDto requestDto);

    List<RequestDtoFull> findRequests(Long userId);

    RequestDtoFull findRequest(Long userId, Long id);

    List<RequestDtoFull> findAllRequests(Long userId, Long from, Long size);
}
