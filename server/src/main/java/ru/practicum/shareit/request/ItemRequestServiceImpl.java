package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundExceptions;
import ru.practicum.shareit.request.dto.ItemRequestAddDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.mapper.ItemRequestListMapper;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRequestMapper itemRequestMapper;
    private final ItemRequestListMapper itemRequestListMapper;

    @Override
    public ItemRequestDto addItemRequest(long userId, ItemRequestAddDto itemRequestAddDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptions("Пользователь с id " + userId + " не найден"));
        ItemRequest newRequest = itemRequestMapper.toItemRequest(itemRequestAddDto);
        newRequest.setRequestor(user);
        newRequest.setDescription(itemRequestAddDto.getDescription());
        newRequest.setCreated(LocalDateTime.now());
        return itemRequestMapper.toDto(itemRequestRepository.save(newRequest));
    }

    @Override
    public List<ItemRequestResponseDto> getItemRequests(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptions("Пользователь с id " + userId + " не найден"));
        List<ItemRequest> itemRequests = itemRequestRepository.findByRequestorIdOrderByCreatedDesc(userId);
        return itemRequestListMapper.toListItemRequestResponseDto(itemRequests);
    }

    @Override
    public List<ItemRequestDto> getAllItemRequests(long userId, int from, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptions("Пользователь с id " + userId + " не найден"));
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "created"));
        Page<ItemRequest> itemRequestPage = itemRequestRepository.findAll(pageRequest);
        return itemRequestPage.getContent().stream()
                .map(itemRequestMapper::toDto)
                .toList();
    }

    @Override
    public ItemRequestResponseDto getItemRequest(long userId, long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptions("Пользователь с id " + userId + " не найден"));
        ItemRequest request = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundExceptions("Реквест с id " + requestId + " не найден"));
        return itemRequestMapper.toItemRequestResponseDto(request);
    }
}
