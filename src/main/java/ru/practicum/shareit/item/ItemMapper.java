package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }

    public static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(null, null, itemDto.getName(),
                itemDto.getDescription(), itemDto.getAvailable(),
                null, null, null);

    }

}
