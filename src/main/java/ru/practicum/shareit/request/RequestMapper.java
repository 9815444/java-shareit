package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoFull;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static RequestDto requestToRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setDescription(request.getDescription());
        return requestDto;
    }

    public static RequestDtoFull requestToRequestDto2(Request request) {
        RequestDtoFull requestDtoFull = new RequestDtoFull();
        requestDtoFull.setId(request.getId());
        requestDtoFull.setUserId(request.getUserId());
        requestDtoFull.setDescription(request.getDescription());
        requestDtoFull.setCreated(request.getCreated());
        List<ItemDtoFull> itemDtoFullList = new ArrayList<>();
        var items = request.getItems();
        for (Item item : items) {
            itemDtoFullList.add(ItemMapper.itemToItemDto2(item));
        }
        requestDtoFull.setItems(itemDtoFullList);
        return requestDtoFull;
    }

    public static Request requestDtoToRequest(RequestDto requestDto) {
        Request request = new Request();
        request.setDescription(requestDto.getDescription());
        return request;
    }

    public static List<RequestDtoFull> listOfRequstToListOfRequestDto2(List<Request> requests) {
        List<RequestDtoFull> requestDtoFullList = new ArrayList<>();
        for (Request request : requests) {
            requestDtoFullList.add(RequestMapper.requestToRequestDto2(request));
        }
        return requestDtoFullList;
    }
}
