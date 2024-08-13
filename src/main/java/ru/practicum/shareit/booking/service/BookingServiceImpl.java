package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.modelException.NotFoundException;
import ru.practicum.shareit.exception.modelException.ValidationException;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final ModelMapper mapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public BookingResponse createBooking(CreateBookingRequest bookingDto, int userId) {
        checkUserExist(userId);
        checkItemExist(bookingDto.getItemId());

        log.info("user '{}' create booking", userId);

        ItemResponse item = itemService.findItemById(bookingDto.getItemId(), userId);
        if (item.getIdOwner() == userId)
            throw new NotFoundException("owner of the thing cannot rent it out to himself");
        if (item.getName() == null) throw new NotFoundException("Item has not be empty");

        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(mapper.map(item, Item.class));
        booking.setBooker(mapper.map(userService.getById(userId), User.class));
        booking.setStatus(Status.WAITING);

        booking = bookingRepository.save(booking);

        return mapper.map(booking, BookingResponse.class);
    }

    @Override
    public BookingResponse confirmOrRejectBooking(int bookingId, boolean approved, int userId) {
        checkUserExist(userId);

        log.info("Confirmation or rejection of the booking request.");
        Booking booking = getBookingById(userId, bookingId);
        if (booking.getItem().getIdOwner().getId() != userId)
            throw new EntityNotFoundException("Only the owner of the item can change the status");
        if (booking.getStatus().equals(Status.APPROVED))
            throw new ValidationException("status cannot be changed to the same");
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else booking.setStatus(Status.REJECTED);

        booking = bookingRepository.save(booking);
        return mapper.map(booking, BookingResponse.class);
    }

    @Override
    public BookingResponse findBookingById(int userId, int bookingId) {
        log.info("find booking by UserId {}", userId);
        Booking booking = getBookingById(userId, bookingId);

        return mapper.map(booking, BookingResponse.class);
    }

    @Override
    public List<BookingResponse> findUserReservationItems(int userId, String state) {
        if (state.equals("UNSUPPORTED_STATUS")) throw new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS");
        checkUserExist(userId);
        Sort sort = Sort.by(Sort.Direction.DESC, "start");

        switch (State.valueOf(state)) {
            case ALL:
                return mapper.map(bookingRepository.findAllByBookerId(userId, sort),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case CURRENT:
                return mapper.map(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(userId, LocalDateTime.now(),
                        LocalDateTime.now()), new TypeToken<List<BookingResponse>>() {
                }.getType());
            case PAST:
                return mapper.map(bookingRepository.findAllByBookerIdAndEndBefore(userId, LocalDateTime.now(), sort),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case FUTURE:
                return mapper.map(bookingRepository.findALlByBookerIdAndStartAfter(userId, LocalDateTime.now(), sort),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case WAITING:
                return mapper.map(bookingRepository.findAllByBookerIdAndStatus(userId, Status.WAITING, sort),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case REJECTED:
                return mapper.map(bookingRepository.findAllByBookerIdAndStatus(userId, Status.REJECTED, sort),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
    }

    @Override
    public List<BookingResponse> findOwnerReservationItems(int ownerId, String state) {
        if (state.equals("UNSUPPORTED_STATUS")) throw new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS");
        User user = getUserById(ownerId);

        switch (State.valueOf(state)) {
            case ALL:
                return mapper.map(bookingRepository.findAllByItemIdOwnerOrderByStartDesc(user),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case CURRENT:
                return mapper.map(bookingRepository.findAllByItemIdOwnerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(user,
                                LocalDateTime.now(), LocalDateTime.now()),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case PAST:
                return mapper.map(bookingRepository.findALLByItemIdOwnerAndEndIsBeforeOrderByStartDesc(user, LocalDateTime.now()),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case FUTURE:
                return mapper.map(bookingRepository.findALLByItemIdOwnerAndStartIsAfterOrderByStartDesc(user, LocalDateTime.now()),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case WAITING:
                return mapper.map(bookingRepository.findAllByItemIdOwnerAndStatusOrderByStartDesc(user, Status.WAITING),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            case REJECTED:
                return mapper.map(bookingRepository.findAllByItemIdOwnerAndStatusOrderByStartDesc(user, Status.REJECTED),
                        new TypeToken<List<BookingResponse>>() {
                        }.getType());
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
    }

    private void checkItemExist(Integer itemId) {
        if (!itemService.findItemById(itemId, 0).getAvailable()) {
            throw new ValidationException("Item is unavailable");
        }
    }

    private void checkUserExist(int userId) {
        userService.getById(userId);
    }

    private User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Booking getBookingById(int userId, int bookingId) {
        Optional<Booking> entity = bookingRepository.findById(bookingId);
        if (entity.isEmpty()) throw new EntityNotFoundException("Booking is not exist");

        Booking booking = entity.get();
        if (booking.getItem().getIdOwner().getId() != userId && booking.getBooker().getId() != userId) {
            throw new EntityNotFoundException("This ID is not available for rent");
        }

        return booking;
    }
}

