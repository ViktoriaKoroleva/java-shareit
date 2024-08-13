package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.dto.BookingResponseDepends;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private int id;
    private int idOwner;
    private String name;
    private String description;
    private Boolean available;

    private BookingResponseDepends lastBooking;
    private BookingResponseDepends nextBooking;

    private List<CommentResponse> comments = new ArrayList<>();
}
