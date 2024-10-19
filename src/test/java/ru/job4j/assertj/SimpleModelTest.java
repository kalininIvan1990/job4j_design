package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleModelTest {

    @Test
    void checkGetName() {
        SimpleModel simple = new SimpleModel();
        assertThatThrownBy(simple::getName)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkSetName() {
        SimpleModel simple = new SimpleModel();
        assertThatThrownBy(() -> simple.setName("name", 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkMessage() {
        SimpleModel simple = new SimpleModel();
        String word = "name";
        int number = 5;
        assertThatThrownBy(() -> simple.setName(word, number))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty();
    }

    @Test
    void checkWordMessage() {
        SimpleModel simple = new SimpleModel();
        String word = "name";
        int number = 5;
        assertThatThrownBy(() -> simple.setName(word, number))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(word, number)
                .hasMessageContaining("this word");

    }
}