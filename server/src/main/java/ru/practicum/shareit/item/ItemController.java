package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDtoFull addItem(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemDto itemDto) {
        return itemService.add(userId, itemDto);
    }


    @PostMapping("/{itemId}/comment")
    public Comment addComment(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody CommentDto commentDto, @PathVariable Long itemId) {
        return itemService.addComment(userId, itemId, commentDto);
    }

    @PatchMapping("/{id}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id, @RequestBody ItemDto itemDto) {
        return itemService.update(userId, id, itemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        itemService.delete(id, userId);
    }

    @GetMapping("/{id}")
    public Item getItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        return itemService.findByUser(id, userId);
    }

    @GetMapping("")
    public List<Item> getUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.findUserItems(userId);
    }

    @GetMapping("/search")
    public List<Item> searching(@RequestParam String text) {
        return itemService.findItemsByText(text);
    }

}
