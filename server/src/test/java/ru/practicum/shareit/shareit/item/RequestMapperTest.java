package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestMapper;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.ArrayList;
import java.util.List;

public class RequestMapperTest {

    @Test
    void testRequestDtoToRequest() {
        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("text");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        var result = RequestMapper.requestDtoToRequest(requestDto);
        Assertions.assertEquals(request.getDescription(), result.getDescription());
    }

    @Test
    void testRequestToRequestDto2() {
        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("text");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        var result = RequestMapper.requestToRequestDto2(request);
        Assertions.assertEquals(request.getDescription(), result.getDescription());
    }

    @Test
    void testListOfRequstToListOfRequestDto2() {
        Request request = new Request();
        request.setId(1L);
        request.setUserId(1L);
        request.setDescription("text");
        request.setCreated(null);
        request.setItems(new ArrayList<>());

        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("text");

        var result = RequestMapper.listOfRequstToListOfRequestDto2(List.of(request));
        Assertions.assertEquals(request.getDescription(), result.get(0).getDescription());
    }
}
