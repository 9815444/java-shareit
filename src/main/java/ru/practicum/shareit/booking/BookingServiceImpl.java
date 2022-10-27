package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoForItem;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.errors.BadRequestException;
import ru.practicum.shareit.errors.NotFoundException;
import ru.practicum.shareit.errors.UnsupportedStatusException;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final ItemRepository itemRepository;

    public User findUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFoundException();
        }
    }

    public Item findItem(Long id) {

        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return item;
        } else {
            throw new NotFoundException();
        }
    }

    public List<Item> findUserItems(Long userId) {
        List<Item> items = itemRepository.findByUserIdOrderById(userId);
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

    public BookingDtoForItem findLast(Long itemId, boolean itsOwner) {
        if (!itsOwner) {
            return null;
        }
        List<Booking> bookings = repository.findByItemIdAndEndBeforeOrderByEndDesc(itemId, LocalDateTime.now());
        if (!bookings.isEmpty()) {
            return bookingForItem(bookings.get(0));
        }
        return null;
    }

    public BookingDtoForItem findNext(Long itemId, boolean itsOwner) {
        if (!itsOwner) {
            return null;
        }
        List<Booking> bookings = repository.findByItemIdAndStartAfterOrderByStart(itemId, LocalDateTime.now());
        if (!bookings.isEmpty()) {
            return bookingForItem(bookings.get(0));
        }
        return null;
    }

    private BookingDtoForItem bookingForItem(Booking booking) {
        return new BookingDtoForItem(booking.getId(), booking.getUserId());
    }

    @Override
    public Booking add(Long userId, BookingDto bookingDto) {
        User user = findUser(userId);
        Item item = findItem(bookingDto.getItemId());
        if (!item.getAvailable()) {
            throw new BadRequestException();
        }
        if (item.getUserId().equals(userId)) {
            throw new NotFoundException();
        }
        if ((bookingDto.getStart().isAfter(bookingDto.getEnd()))
                || bookingDto.getStart().isBefore(LocalDateTime.now())
                || bookingDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new BadRequestException();
        }
        Booking booking = BookingMapper.bookingDtoToBooking(userId, bookingDto);
        booking.setUserId(userId);
        booking.setStatus(Status.WAITING.toString());
        booking.setBooker(user);
        booking.setItem(item);
        return repository.save(booking);
    }

    @Override
    public Booking approve(Long userId, Long bookingId, Boolean approved) {
        Booking booking = find(userId, bookingId);
        if (!(booking.getItem().getUserId().equals(userId))) {
            throw new NotFoundException();
        }
        if ((booking.getStatus().equals(Status.APPROVED.toString()))
                || (booking.getStatus().equals(Status.REJECTED.toString()))) {
            throw new BadRequestException();
        }
        if (approved) {
            booking.setStatus(Status.APPROVED.toString());
        } else {
            booking.setStatus(Status.REJECTED.toString());
        }
        repository.save(booking);
        return booking;
    }

    @Override
    public Booking find(Long userId, Long id) {
        Optional<Booking> bookingOptional = repository.findById(id);
        if (!bookingOptional.isPresent()) {
            throw new NotFoundException();
        }
        Booking booking = bookingOptional.get();
        Item item = findItem(booking.getItemId());
        if (!((booking.getUserId().equals(userId)) || (item.getUserId().equals(userId)))) {
            throw new NotFoundException();
        }
        booking.setItem(item);
        User user = findUser(booking.getUserId());
        booking.setBooker(user);
        return booking;
    }

    @Override
    public List<Booking> findAll(Long userId, String state) {
        if (userId == null) {
            throw new NotFoundException();
        }
        if (state == null) {
            state = "ALL";
        }
        User user = findUser(userId);
        if (state.equals("ALL")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId)
                    .stream().map((booking -> addItemAndBooker(booking))).collect(Collectors.toList());
            return bookings;
        } else if (state.equals("FUTURE")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("CURRENT")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking ->
                            booking.getStart().isBefore(LocalDateTime.now())
                                    && booking.getEnd().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("PAST")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("REJECTED")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("REJECTED"))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("WAITING")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("WAITING"))
                    .collect(Collectors.toList());
            return bookings;
        } else {
            throw new UnsupportedStatusException(state);
        }
    }

    @Override
    public List<Booking> findAll(Long userId, String state, Long from, Long size) {

        if ((from == null) || (size == null)) {
            return findAll(userId, state);
        }
        if ((from < 0) || (size <= 0)) {
            throw new BadRequestException();
        }

        if (userId == null) {
            throw new NotFoundException();
        }
        if (state == null) {
            state = "ALL";
        }

        Long countBooking =  Long.valueOf(repository.findByUserIdOrderByStartDesc(userId).size());


        int fromPage = from.intValue() / size.intValue();
        Pageable pageable = PageRequest.of(fromPage, size.intValue());

        User user = findUser(userId);

        if (state.equals("ALL")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking))).collect(Collectors.toList());
            return bookings;
        } else if (state.equals("FUTURE")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("CURRENT")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking ->
                            booking.getStart().isBefore(LocalDateTime.now())
                                    && booking.getEnd().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("PAST")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("REJECTED")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("REJECTED"))
                    .collect(Collectors.toList());
            return bookings;
        } else if (state.equals("WAITING")) {
            List<Booking> bookings = repository.findByUserIdOrderByStartDesc(userId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("WAITING"))
                    .collect(Collectors.toList());
            return bookings;
        } else {
            throw new UnsupportedStatusException(state);
        }
    }

    @Override
    public List<Booking> findAllByOwner(Long ownerId, String state, Long from, Long size) {

        if ((from == null) || (size == null)) {
            return findAllByOwner(ownerId, state);
        }
        if ((from < 0) || (size <= 0)) {
            throw new BadRequestException();
        }

        if (ownerId == null) {
            throw new NotFoundException();
        }
        if (state == null) {
            state = "ALL";
        }

        int fromPage = from.intValue() / size.intValue();
        Pageable pageable = PageRequest.of(fromPage, size.intValue());

        User owner = findUser(ownerId);
        List<Long> itemsId = findUserItems(ownerId).stream()
                .map((item -> item.getId())).collect(Collectors.toList());
        if (state.equals("ALL")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .collect(Collectors.toList());
        } else if (state.equals("FUTURE")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
        } else if (state.equals("CURRENT")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking ->
                            booking.getStart().isBefore(LocalDateTime.now())
                                    && booking.getEnd().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
        } else if (state.equals("PAST")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
        } else if (state.equals("REJECTED")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("REJECTED"))
                    .collect(Collectors.toList());
        } else if (state.equals("WAITING")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId, pageable).getContent()
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("WAITING"))
                    .collect(Collectors.toList());
        } else {
            throw new UnsupportedStatusException(state);
        }
    }

    @Override
    public List<Booking> findAllByOwner(Long ownerId, String state) {
        if (ownerId == null) {
            throw new NotFoundException();
        }
        if (state == null) {
            state = "ALL";
        }
        User owner = findUser(ownerId);
        List<Long> itemsId = findUserItems(ownerId).stream()
                .map((item -> item.getId())).collect(Collectors.toList());
        if (state.equals("ALL")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .collect(Collectors.toList());
        } else if (state.equals("FUTURE")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
        } else if (state.equals("CURRENT")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking ->
                            booking.getStart().isBefore(LocalDateTime.now())
                                    && booking.getEnd().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
        } else if (state.equals("PAST")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
        } else if (state.equals("REJECTED")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("REJECTED"))
                    .collect(Collectors.toList());
        } else if (state.equals("WAITING")) {
            return repository.findByItemIdInOrderByStartDesc(itemsId)
                    .stream().map((booking -> addItemAndBooker(booking)))
                    .filter(booking -> booking.getStatus().equals("WAITING"))
                    .collect(Collectors.toList());
        } else {
            throw new UnsupportedStatusException(state);
        }
    }

    private Booking addItemAndBooker(Booking booking) {
        Item item = findItem(booking.getItemId());
        booking.setItem(item);
        User user = findUser(booking.getUserId());
        booking.setBooker(user);
        return booking;
    }
}
