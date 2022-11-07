package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoForItem;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.errors.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoFull;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final BookingRepository bookingRepository;

    private final RequestRepository requestRepository;

    @Override
    public ItemDtoFull add(Long userId, ItemDto itemDto) {
        Item item = ItemMapper.itemDtoToItem(itemDto);
        item.setUserId(userId);
        if (itemDto.getRequestId() != null) {
            Request request = requestRepository.findById(itemDto.getRequestId()).orElseThrow();
            request.addItem(item);
        }
        var savedItem = repository.save(item);
        return ItemMapper.itemToItemDto2(savedItem);
    }

    @Override
    public Item update(Long userId, Long id, ItemDto itemDto) {

        Item item = find(id);

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        return repository.save(item);

    }

    @Override
    public void delete(Long id, Long userId) {
        repository.delete(find(id));
    }

    @Override
    public Item find(Long id) {

        Optional<Item> optionalItem = repository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return item;
        } else {
            throw new NotFoundException();
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
            throw new NotFoundException();
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
            throw new BadRequestException();
        }
        List<Booking> bookings =
                bookingRepository.findByUserIdAndItemIdAndStatusAndEndBefore(userId, itemId,
                        Status.APPROVED.toString(), LocalDateTime.now());
        if (bookings.isEmpty()) {
            throw new BadRequestException();
        }
        commentDto.setCreated(LocalDateTime.now());
        User user = userRepository.findById(userId).get();
        Comment comment = CommentMapper.commentDtoToComment(commentDto, userId, itemId);
        comment.setAuthorName(user.getName());
        return commentRepository.save(comment);
    }
}
