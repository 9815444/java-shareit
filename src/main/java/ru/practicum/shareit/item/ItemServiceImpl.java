package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.errors.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemRepository1 repository;
    private final BookingRepository bookingRepository;
    private final ItemMapper itemMapper;
    //    private final BookingMapper bookingMapper;
    private final ItemValidation validation;

    @Override
    public Item add(Long userId, ItemDto itemDto) {
        validation.itemIsValidAdd(userId, itemDto);
        Item item = itemMapper.itemDtoToItem(itemDto);
        item.setUserId(userId);
//        item.setUserId(userId);
//        return itemRepository.add(item);
        return repository.save(item);
    }

    @Override
    public Item update(Long userId, Long id, ItemDto itemDto) {

        validation.itemIsValidUpdate(userId, id);

        Item item = find(id);
        Item updatedItem = new Item(id, userId, item.getName()
                , item.getDescription(), item.getAvailable(), null, null);

        if (itemDto.getName() != null) {
            updatedItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            updatedItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            updatedItem.setAvailable(itemDto.getAvailable());
        }

//        return itemRepository.update(id, updatedItem);
        return repository.save(updatedItem);

    }

    @Override
    public void delete(Long id, Long userId) {
        validation.itemIsValidDelete(userId, id);
//        itemRepository.delete(id);
        repository.delete(find(id));
    }

    @Override
    public Item find(Long id) {

        Optional<Item> optionalItem = repository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setLastBooking(findLast(id));
            item.setNextBooking(findNext(id));
            return item;
        } else {
            throw new NotFound();
        }
//        return itemRepository.find(id);
    }

    public BookingDto findLast(Long itemId) {
        List<Booking> bookings = bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(itemId, LocalDateTime.now());
        if (!bookings.isEmpty()) {
            return bookingForItem(bookings.get(0));
        }
        return null;
    }



    public BookingDto findNext(Long itemId) {
        List<Booking> bookings = bookingRepository.findByItemIdAndStartAfterOrderByStart(itemId, LocalDateTime.now());
        if (!bookings.isEmpty()) {
            return bookingForItem(bookings.get(0));
        }
        return null;
    }

    private BookingDto bookingForItem(Booking booking) {
        return new BookingDto(booking.getItemId(), booking.getUserId(), booking.getStart(), booking.getEnd());
    }

    @Override
    public List<Item> findUserItems(Long userId) {
        return repository.findByUserId(userId);
//        return itemRepository.findUserItems(userId);
    }

    @Override
    public List<Item> findItemsByText(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        } else {
            return repository.findByAvailableAndDescriptionContainingIgnoreCaseOrAvailableAndNameContainingIgnoreCase(true, text, true, text);
//        return itemRepository.findItemsByText(text);
        }
    }
}
