package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item add(Long userId, ItemDto itemDto);

    Item update(Long userId, Long id, ItemDto itemDto);

    void delete(Long id, Long userId);

    Item find(Long id);
    Item findByUser(Long id, Long userId);

    List<Item> findUserItems(Long userId);

    List<Item> findItemsByText(String text);

    Comment addComment(Long userId, Long itemId, CommentDto commentDto);
}
