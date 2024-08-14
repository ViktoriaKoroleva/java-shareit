package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.mapper.CommentListMapper;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.exception.RuleViolationException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;
import ru.practicum.shareit.item.mapper.ItemListMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final ItemListMapper itemListMapper;
    private final CommentMapper commentMapper;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final BookingMapper bookingMapper;
    private final CommentListMapper commentListMapper;


    @Override
    public ItemDto createItemForOwner(String owner, ItemDto itemDto) {
        long ownerId = Long.parseLong(owner);
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + ownerId + " не найден"));
        Item item = itemMapper.toModel(itemDto);
        item.setOwner(user);
        Item itemFromRep = itemRepository.save(item);
        return itemMapper.toDto(itemFromRep);
    }

    @Override
    public ItemDto updateItem(String owner, ItemDto itemDto) {
        long ownerId = Long.parseLong(owner);
        User possibleUser = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + ownerId + " не найден"));
        if (itemDto.getId() == null) {
            throw new NotFoundException("Не передан идентификатор для обновления");
        }
        Item returnedItem = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> new NotFoundException("Вещь с id " + itemDto.getId() + " не найдена"));
        if (returnedItem.getOwner().getId() != ownerId) {
            throw new RuleViolationException("Редактировать вещь имеет право только её владелец");
        }

        if (itemDto.getName() == null || itemDto.getName().isEmpty()) {
            itemDto.setName(returnedItem.getName());
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) {
            itemDto.setDescription(returnedItem.getDescription());
        }
        if (itemDto.getAvailable() == null) {
            itemDto.setAvailable(returnedItem.getAvailable());
        }

        Item item = itemMapper.toModel(itemDto);
        item.setOwner(possibleUser);
        Item updatedItem = itemRepository.save(item);
        return itemMapper.toDto(updatedItem);
    }

    @Override
    public ItemInfoDto getItem(String owner, long itemId) {
        long ownerId = Long.parseLong(owner);
        userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + ownerId + " не найден"));
        Item returnedItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + itemId + " не найдена"));
        ItemInfoDto itemInfoDto = itemMapper.toItemInfoDto(returnedItem);
        itemInfoDto.setComments(commentListMapper.toListDto(commentRepository.findAllByItemId(itemId)));
        return itemInfoDto;
    }

    @Override
    public List<ItemDto> getAllByOwner(String owner) {
        long ownerId = Long.parseLong(owner);
        return itemListMapper.toListDto(itemRepository.findByOwnerId(ownerId));
    }

    @Override
    public List<ItemDto> getSelection(String searchText) {
        if (searchText.isEmpty() || searchText.isBlank()) {
            return List.of();
        }
        return itemListMapper.toListDto(itemRepository.search(searchText));
    }

    @Override
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найдена"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + itemId + " не найдена"));
        Comment comment = commentMapper.toModel(commentDto);
        if (comment == null || comment.getText() == null) {
            throw new ValidationException("Комментарий не должен быть пустым");
        }
        if (bookingRepository.findAllByBookerIdAndItemIdAndStatusAndEndBefore(userId, itemId, BookingStatus.APPROVED,
                LocalDateTime.now()).isEmpty()) {
            throw new ValidationException("Пользователь с id = " + userId + " не получал item с id = " + itemId);
        }
        comment.setAuthor(user);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());
        CommentDto savedComment = commentMapper.toDto(commentRepository.save(comment));
        savedComment.setAuthorName(user.getName());
        savedComment.setItemId(item.getId());
        return savedComment;
    }
}