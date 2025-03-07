package ru.job4j.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ConfigTest {
    @Test
    void whenPairWithCommentAndEmptyString() {
        String path = "./data/pair_with_comment_and_empty_strings.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.dialect")).isEqualTo("org.hibernate.dialect.PostgreSQLDialect");
        assertThat(config.value("hibernate.connection.url")).isEqualTo("jdbc:postgresql://127.0.0.1:5432/trackstudio");
        assertThat(config.value("hibernate.connection.driver_class")).isEqualTo("org.postgresql.Driver");
    }

    @Test
    void whenValueWithoutKeyThenIllegalArgumentException() {
        String path = "./data/invalid_without_key.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid format");

    }

    @Test
    void whenKeyWithoutValueThenIllegalArgumentException() {
        Config config = new Config("./data/invalid_without_value.properties");
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid");
    }

    @Test
    void whenKeyAndValueWithoutSymbol() {
        Config config = new Config("./data/invalid_without_symbol.properties");
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("format");
    }

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.connection.password")).isEqualTo("password=1");
    }
}