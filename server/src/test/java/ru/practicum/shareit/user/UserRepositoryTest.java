package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserRepositoryTest {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private final User user = User.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .build();

    @BeforeEach
    public void setUp() {
        testEntityManager.persist(user);
        testEntityManager.flush();
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        testEntityManager.remove(user);
    }

    @Test
    void findByEmail() {
        List<User> userByEmail = userRepository.findByEmail("john.doe@example.com");
        assertEquals(userByEmail.getFirst(), user);
    }
}