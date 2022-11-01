package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.mail.FetchProfile;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;

    private final ItemValidation itemValidation;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemDto itemDto) {
        itemValidation.itemIsValidAdd(userId, itemDto);
        return itemClient.addItem(userId, itemDto);
    }


    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody CommentDto commentDto, @PathVariable Long itemId) {
        return itemClient.addComment(userId, itemId, commentDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id, @Valid @RequestBody ItemDto itemDto) {
        itemValidation.itemIsValidUpdate(userId, id);
        return itemClient.updateItem(userId, id, itemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        itemValidation.itemIsValidDelete(userId, id);
        itemClient.deleteItem(id, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        return itemClient.getItem(userId, id);
    }

    @GetMapping("")
    public ResponseEntity<Object> getUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getUserItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searching(@RequestParam String text) {
        return itemClient.searching(text);
    }

}