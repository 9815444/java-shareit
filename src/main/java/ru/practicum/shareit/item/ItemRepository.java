package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUserIdOrderById(Long userId);

    List<Item> findByAvailableAndDescriptionContainingIgnoreCaseOrAvailableAndNameContainingIgnoreCase(Boolean available, String text, Boolean available1, String text1);
}