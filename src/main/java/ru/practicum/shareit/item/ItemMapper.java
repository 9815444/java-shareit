package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable(), item.getRequest().getId());
    }

    public static ItemDtoFull itemToItemDto2(Item item) {
        Long requestId = null;
        if (item.getRequest() != null) {
            requestId = item.getRequest().getId();
        }
        return new ItemDtoFull(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                requestId, item.getLastBooking(), item.getNextBooking(), item.getComments());
    }

    public static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(null, null,  null, itemDto.getName(),
                itemDto.getDescription(), itemDto.getAvailable(),
                null, null, null);

    }

}
