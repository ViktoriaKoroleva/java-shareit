package ru.practicum.shareit.request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ItemRequestRepositoryTest {

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    private ItemRequest request;

    private final User user = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .build();


    @BeforeEach
    void setUp() {
        userRepository.save(user);
        request = new ItemRequest();
        request.setId(1L);
        request.setRequestor(user);
        request.setDescription("Description");
        request.setCreated(LocalDateTime.now());
        itemRequestRepository.save(request);
        Item item = Item.builder()
                .id(1L)
                .name("Item Name")
                .description("Item Description")
                .available(true)
                .request(request)
                .build();
        itemRepository.save(item);
        request.setItems(List.of(item));
        itemRequestRepository.save(request);
    }

    @AfterEach
    void tearDown() {
        itemRequestRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByRequestorIdOrderByCreatedDesc() {
        List<ItemRequest> result = itemRequestRepository.findByRequestorIdOrderByCreatedDesc(user.getId());
        assertEquals(result.size(), 1);
        assertEquals(result.getFirst().getId(), request.getId());
    }
}