package ru.yandex.practicum;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.practicum.configuration.DataSourceConfiguration;
import ru.yandex.practicum.configuration.ThymeleafConfiguration;

@Testcontainers
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BaseIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    protected static AnnotationConfigWebApplicationContext webContext;
    protected MockMvc mockMvc;

    @BeforeAll
    static void setUpClass() {
        System.setProperty("datasource.url", postgres.getJdbcUrl());
        System.setProperty("datasource.username", postgres.getUsername());
        System.setProperty("datasource.password", postgres.getPassword());
        System.setProperty("datasource.driver", "org.postgresql.Driver");

        webContext = new AnnotationConfigWebApplicationContext();
        webContext.setServletContext(new MockServletContext());
        webContext.register(WebConfiguration.class);
        webContext.register(DataSourceConfiguration.class);
        webContext.register(ThymeleafConfiguration.class);
        webContext.refresh();
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @AfterAll
    static void tearDown() {
        webContext.close();
    }
}
