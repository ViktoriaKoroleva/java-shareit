package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByBookerId(int bookerId, Sort sort);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfter(int bookerId, LocalDateTime s, LocalDateTime s1);

    List<Booking> findAllByBookerIdAndStatus(int bookerId, Status status, Sort sort);

    List<Booking> findALlByBookerIdAndStartAfter(int bookerId, LocalDateTime end, Sort sort);

    List<Booking> findAllByBookerIdAndEndBefore(int bookerId, LocalDateTime end, Sort sort);


    List<Booking> findAllByItemIdOwnerOrderByStartDesc(User ownerId);

    List<Booking> findAllByItemIdOwnerAndStatusOrderByStartDesc(User ownerId, Status status);


    List<Booking> findAllByItemIdOwnerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(User ownerId, LocalDateTime start,
                                                                                    LocalDateTime end);

    List<Booking> findALLByItemIdOwnerAndStartIsAfterOrderByStartDesc(User ownerId, LocalDateTime start);

    List<Booking> findALLByItemIdOwnerAndEndIsBeforeOrderByStartDesc(User ownerId, LocalDateTime end);


    Optional<Booking> findFirstByItemIdAndStatusAndStartBeforeOrderByEndDesc(int itemId, Status status, LocalDateTime start);

    Optional<Booking> findFirstByItemIdAndStatusAndStartAfterOrderByStart(int itemId, Status status, LocalDateTime start);

    List<Booking> findAllByBookerIdAndItemIdAndEndBeforeAndStatus(int bookerId, int itemId,
                                                                  LocalDateTime end, Status status);

    @EntityGraph(attributePaths = {"item", "booker"})
    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds AND b.status = :status AND b.start < :currentDate")
    List<Booking> findAllLastBookingsByItemsAndStatus(@Param("itemIds") List<Integer> itemIds,
                                                      @Param("status") Status status,
                                                      @Param("currentDate") LocalDateTime currentDate);


    @EntityGraph(attributePaths = {"item", "booker"})
    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds AND b.status = :status AND b.start > :currentDate")
    List<Booking> findAllNextBookingsByItemsAndStatus(@Param("itemIds") List<Integer> itemIds,
                                                      @Param("status") Status status,
                                                      @Param("currentDate") LocalDateTime currentDate);
}
