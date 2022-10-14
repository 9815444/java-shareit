package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoForItem;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.errors.BadRequest;
import ru.practicum.shareit.errors.NotFound;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository1 repository;

    private final CommentRepository commentRepository;

    private final BookingRepository bookingRepository;

    private final CommentMapper commentMapper;

    private final ItemValidation validation;

    @Override
    public Item add(Long userId, ItemDto itemDto) {
        validation.itemIsValidAdd(userId, itemDto);
        Item item = ItemMapper2.itemDtoToItem(itemDto);
        item.setUserId(userId);
        return repository.save(item);
    }

    @Override
    public Item update(Long userId, Long id, ItemDto itemDto) {

        validation.itemIsValidUpdate(userId, id);

        Item item = find(id);
        Item updatedItem = new Item(id, userId, item.getName(),
                item.getDescription(), item.getAvailable(), null, null, null);

        if (itemDto.getName() != null) {
            updatedItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            updatedItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            updatedItem.setAvailable(itemDto.getAvailable());
        }

        return repository.save(updatedItem);

    }

    @Override
    public void delete(Long id, Long userId) {
        validation.itemIsValidDelete(userId, id);
        repository.delete(find(id));
    }

    @Override
    public Item find(Long id) {

        Optional<Item> optionalItem = repository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return item;
        } else {
            throw new NotFound();
        }
    }

    public Item findByUser(Long id, Long userId) {

        Optional<Item> optionalItem = repository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            boolean itsOwner = false;
            if (item.getUserId().equals(userId)) {
                itsOwner = true;
            }
            item.setLastBooking(findLast(id, itsOwner));
            item.setNextBooking(findNext(id, itsOwner));
            List<Comment> comments = commentRepository.findByItemId(item.getId());
            item.setComments(comments);
            return item;
        } else {
            throw new NotFound();
        }
    }

    public BookingDtoForItem findLast(Long itemId, boolean itsOwner) {
        if (!itsOwner) {
            return null;
        }
        List<Booking> bookings = bookingRepository.findByItemIdAndEndBeforeOrderByEndDesc(itemId, LocalDateTime.now());
        if (!bookings.isEmpty()) {
            return bookingForItem(bookings.get(0));
        }
        return null;
    }

    public BookingDtoForItem findNext(Long itemId, boolean itsOwner) {
        if (!itsOwner) {
            return null;
        }
        List<Booking> bookings = bookingRepository.findByItemIdAndStartAfterOrderByStart(itemId, LocalDateTime.now());
        if (!bookings.isEmpty()) {
            return bookingForItem(bookings.get(0));
        }
        return null;
    }

    private BookingDtoForItem bookingForItem(Booking booking) {
        return new BookingDtoForItem(booking.getId(), booking.getUserId());
    }

    @Override
    public List<Item> findUserItems(Long userId) {
        List<Item> items = repository.findByUserIdOrderById(userId);
        for (Item item : items) {
            boolean itsOwner = false;
            if (item.getUserId().equals(userId)) {
                itsOwner = true;
            }
            item.setLastBooking(findLast(item.getId(), itsOwner));
            item.setNextBooking(findNext(item.getId(), itsOwner));
            item.setComments(commentRepository.findByItemId(item.getId()));
        }
        return items;
    }

    @Override
    public List<Item> findItemsByText(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        } else {
            return repository.findByAvailableAndDescriptionContainingIgnoreCaseOrAvailableAndNameContainingIgnoreCase(true, text, true, text);
        }
    }

    @Override
    public Comment addComment(Long userId, Long itemId, CommentDto commentDto) {
        if (commentDto.getText().isEmpty()) {
            throw new BadRequest();
        }
        List<Booking> bookings =
                bookingRepository.findByUserIdAndItemIdAndStatusAndEndBefore(userId, itemId,
                        Status.APPROVED.toString(), LocalDateTime.now());
        if (bookings.isEmpty()) {
            throw new BadRequest();
        }
        commentDto.setCreated(LocalDateTime.now());
        Comment comment = commentMapper.commentDtoToComment(commentDto, userId, itemId);
        return commentRepository.save(comment);
    }
}
