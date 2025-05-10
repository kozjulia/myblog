package ru.yandex.practicum.repository;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.configuration.DataSourceConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, JdbcNativePostRepository.class})
@TestPropertySource(locations = "classpath:test-application.properties")
class JdbcNativePostRepositoryTest {

  /*  @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        // Очистка базы данных
        jdbcTemplate.execute("DELETE FROM users");

        // Добавление тестовых данных
        jdbcTemplate.execute("INSERT INTO users (id, first_name, last_name, age, active) VALUES (1, 'Иван', 'Иванов', 30, true)");
        jdbcTemplate.execute("INSERT INTO users (id, first_name, last_name, age, active) VALUES (2, 'Петр', 'Петров', 25, false)");
        jdbcTemplate.execute("INSERT INTO users (id, first_name, last_name, age, active) VALUES (3, 'Мария', 'Сидорова', 28, true)");
    }

    @Test
    void save_shouldAddUserToDatabase() {
        Post user = new Post(4L, "Петр", "Васильев", 25, true);

        postRepository.save(user);

        Post savedUser = postRepository.findAll().stream()
                .filter(createdUsers -> createdUsers.getId().equals(4L))
                .findFirst()
                .orElse(null);

        assertNotNull(savedUser);
        assertEquals("Петр", savedUser.getFirstName());
        assertEquals("Васильев", savedUser.getLastName());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        List<Post> users = postRepository.findAll();

        assertNotNull(users);
        assertEquals(3, users.size());

        Post user = users.getFirst();
        assertEquals(1L, user.getId());
        assertEquals("Иван", user.getFirstName());
    }

    @Test
    void deleteById_shouldRemoveUserFromDatabase() {
        postRepository.deleteById(1L);

        List<Post> users = postRepository.findAll();

        Post deletedUser = users.stream()
                .filter(createdUsers -> createdUsers.getId().equals(1L))
                .findFirst()
                .orElse(null);
        assertNull(deletedUser);
    } */
}
