package ru.job4j.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddAfterWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addAfter(input, 4, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenRemoveIfExecuted() {
        ListUtils.removeIf(input, n -> n % 2 == 1);
        assertThat(input).isEmpty();
    }

    @Test
    void whenRemoveIfNotExecuted() {
        ListUtils.removeIf(input, n -> n % 2 == 0);
        assertThat(input).hasSize(2).containsSequence(1, 3);
    }

    @Test
    void whenReplaceIfExecuted() {
        ListUtils.replaceIf(input, n -> n == 3, 2);
        assertThat(input).hasSize(2).containsSequence(1, 2);
    }

    @Test
    void whenReplaceIfNotExecuted() {
        ListUtils.replaceIf(input, n -> n == 4, 2);
        assertThat(input).hasSize(2).containsSequence(1, 3);
    }

    @Test
    void whenRemoveAllExecuted() {
        input.add(4);
        List<Integer> newList = new ArrayList<>(Arrays.asList(1, 3, 4));
        ListUtils.removeAll(input, newList);
        assertThat(input).isEmpty();
    }

    @Test
    void whenRemoveAllNotExecuted() {
        List<Integer> newList = new ArrayList<>(Arrays.asList(0, 2));
        ListUtils.removeAll(input, newList);
        assertThat(input).hasSize(2).containsSequence(1, 3);
    }
}