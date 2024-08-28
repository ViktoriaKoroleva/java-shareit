package ru.practicum.shareit.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    private final User user = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .build();

    private final Item item = Item.builder()
            .id(1L)
            .name("Item 1")
            .description("Item 1")
            .available(true)
            .owner(user)
            .build();

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        itemRepository.save(item);
    }

    @AfterEach
    void tearDown() {
        itemRepository.delete(item);
        userRepository.delete(user);
    }

    @Test
    void findAllByOwnerId() {
        List<Item> allByOwnerId = itemRepository.findByOwnerId(user.getId());
        assertEquals(allByOwnerId.size(), 1);
        assertEquals(allByOwnerId.getFirst(), item);
    }

    @Test
    void searchText() {
        List<Item> items = itemRepository.search("Item 1");
        assertEquals(items.size(), 1);
        assertEquals(items.getFirst(), item);
    }
}