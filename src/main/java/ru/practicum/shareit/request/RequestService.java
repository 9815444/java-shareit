package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDto2;

import java.util.List;

public interface RequestService {

    Request addRequest(Long userId, RequestDto requestDto);

    List<RequestDto2> findRequests(Long userId);

    RequestDto2 findRequest(Long userId, Long id);

    List<RequestDto2> findAllRequests(Long userId, Long from, Long size);
}
