package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto2;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDto2;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static RequestDto requestToRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setDescription(request.getDescription());
        return requestDto;
    }

    public static RequestDto2 requestToRequestDto2(Request request) {
        RequestDto2 requestDto2 = new RequestDto2();
        requestDto2.setId(request.getId());
        requestDto2.setUserId(request.getUserId());
        requestDto2.setDescription(request.getDescription());
        requestDto2.setCreated(request.getCreated());
        List<ItemDto2> itemDto2List = new ArrayList<>();
        var items = request.getItems();
        for (Item item : items) {
            itemDto2List.add(ItemMapper.itemToItemDto2(item));
        }
        requestDto2.setItems(itemDto2List);
        return requestDto2;
    }

    public static Request requestDtoToRequest(RequestDto requestDto) {
        Request request = new Request();
        request.setDescription(requestDto.getDescription());
        return request;
    }

    public static List<RequestDto2> listOfRequstToListOfRequestDto2(List<Request> requests) {
        List<RequestDto2> requestDto2List = new ArrayList<>();
        for (Request request : requests) {
            requestDto2List.add(RequestMapper.requestToRequestDto2(request));
        }
        return requestDto2List;
    }
}
