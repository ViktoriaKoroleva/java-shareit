package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {

    Booking toModel(BookingDto bookingDto);

    BookingDto toDto(Booking booking);

}
