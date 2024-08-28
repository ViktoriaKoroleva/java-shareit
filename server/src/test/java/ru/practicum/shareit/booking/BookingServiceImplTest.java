package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingServiceImplTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private Booking booking;
    private BookingDto bookingDto;
    private BookingRequestDto bookingRequestDto;
    private User user;
    private User owner;
    private Item item;
    private final BookingMapper bookingMapper;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        bookingService = new BookingServiceImpl(bookingRepository, userRepository, itemRepository, bookingMapper);
        user = User.builder()
                .id(1L)
                .name("TestUserName")
                .email("UserEmail@test.com")
                .build();
        userRepository.save(user);
        owner = User.builder()
                .id(2L)
                .name("TestOwnerName")
                .email("OwnerEmail@test.com")
                .build();
        userRepository.save(owner);
        item = Item.builder()
                .id(1L)
                .name("TestItemName")
                .description("TestItemDescription")
                .request(null)
                .available(true)
                .owner(owner)
                .build();
        itemRepository.save(item);
        itemDto = ItemDto.builder()
                .id(1L)
                .name("TestItemDtoName")
                .description("TestItemDtoDescription")
                .available(true)
                .requestId(1L)
                .build();
        booking = Booking.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .status(BookingStatus.WAITING)
                .booker(user)
                .item(item)
                .build();
        bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setItemId(item.getId());
        bookingRequestDto.setStart(booking.getStart());
        bookingRequestDto.setEnd(booking.getEnd());
        bookingDto = bookingMapper.toDto(booking);

        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingRepository.findByBookerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));
    }

    @Test
    void create() {
        final BookingDto result = bookingService.saveBooking(user.getId(), bookingRequestDto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStart(), bookingDto.getStart());
        Assertions.assertEquals(result.getEnd(), bookingDto.getEnd());
        Assertions.assertEquals(result.getItem(), bookingDto.getItem());
        Assertions.assertEquals(result.getStatus(), bookingDto.getStatus());
        Assertions.assertEquals(result.getBooker(), bookingDto.getBooker());
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void setApproved() {
        BookingDto result = bookingService.setApproved(owner.getId(), booking.getId(), true);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStart(), bookingDto.getStart());
        Assertions.assertEquals(result.getEnd(), bookingDto.getEnd());
        Assertions.assertEquals(result.getItem(), bookingDto.getItem());
        Assertions.assertEquals(result.getStatus(), BookingStatus.APPROVED.toString());
        Assertions.assertEquals(result.getBooker(), bookingDto.getBooker());
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void findById() {
        BookingDto result = bookingService.getBooking(booking.getId(), user.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStart(), bookingDto.getStart());
        Assertions.assertEquals(result.getEnd(), bookingDto.getEnd());
        Assertions.assertEquals(result.getItem(), bookingDto.getItem());
        Assertions.assertEquals(result.getStatus(), bookingDto.getStatus());
        Assertions.assertEquals(result.getBooker(), bookingDto.getBooker());
        verify(bookingRepository, times(1)).findById(anyLong());
    }

    @Test
    void findAllByBookerAndStatus() {
        List<BookingDto> result = bookingService.getAllByBookerAndStatus(user.getId(), "ALL").stream().toList();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getFirst().getStart(), bookingDto.getStart());
        Assertions.assertEquals(result.getFirst().getEnd(), bookingDto.getEnd());
        Assertions.assertEquals(result.getFirst().getItem(), bookingDto.getItem());
        Assertions.assertEquals(result.getFirst().getStatus(), bookingDto.getStatus());
        Assertions.assertEquals(result.getFirst().getBooker(), bookingDto.getBooker());
        verify(bookingRepository, times(1)).findByBookerIdOrderByStartDesc(anyLong());
    }

    @Test
    void findAllByOwnerAndStatus() {
        List<BookingDto> result = bookingService.getAllByOwnerAndStatus(owner.getId(), "ALL").stream().toList();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getFirst().getStart(), bookingDto.getStart());
        Assertions.assertEquals(result.getFirst().getEnd(), bookingDto.getEnd());
        Assertions.assertEquals(result.getFirst().getItem(), bookingDto.getItem());
        Assertions.assertEquals(result.getFirst().getStatus(), bookingDto.getStatus());
        Assertions.assertEquals(result.getFirst().getBooker(), bookingDto.getBooker());
        verify(bookingRepository, times(1)).findAllByItemOwnerIdOrderByStartDesc(anyLong());
    }
}