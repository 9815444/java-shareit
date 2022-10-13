package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface ItemRepository1 extends JpaRepository<Item, Long> {
    List<Item> findByUserIdOrderById(Long userId);
    List<Item> findByAvailableAndDescriptionContainingIgnoreCaseOrAvailableAndNameContainingIgnoreCase(Boolean available, String text, Boolean available1, String text1);
}