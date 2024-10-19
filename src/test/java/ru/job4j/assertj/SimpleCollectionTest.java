package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class SimpleCollectionTest {

    @Test
    void generalAssert() {
        SimpleCollection<Integer> simpleCollection = new SimpleCollection<>(1, 1, 3, 4, 5);
        assertThat(simpleCollection).isNotEmpty()
                .hasSize(5)
                .contains(1, 3, 5)
                .containsOnly(1, 5, 4, 3)
                .containsExactly(1, 1, 3, 4, 5)
                .containsExactlyInAnyOrder(3, 1, 1, 5, 4)
                .containsAnyOf(400, 300, 1)
                .doesNotContain(43, 21)
                .startsWith(1, 1)
                .endsWith(4, 5)
                .containsSequence(3, 4);
    }

    @Test
    void satisfyAssert() {
        SimpleCollection<Integer> simpleCollection = new SimpleCollection<>(1, 1, 3, 4, 5);
        assertThat(simpleCollection).isNotNull()
                .allSatisfy(e -> {
                    assertThat(e).isLessThan(10);
                    assertThat(e).isGreaterThan(0);
                })
                .anySatisfy(e -> {
                    assertThat(e).isLessThan(5);
                    assertThat(e).isEqualTo(3);

                })
                .allMatch(e -> e < 10)
                .anyMatch(e -> e == 5)
                .noneMatch(e -> e < 1);
    }

    @Test
    void checkNavigationList() {
        SimpleCollection<Integer> simpleCollection = new SimpleCollection<>(1, 1, 3, 4, 5);
        assertThat(simpleCollection).first()
                .isEqualTo(1);
        assertThat(simpleCollection).element(2)
                .isNotNull()
                .isEqualTo(3);
        assertThat(simpleCollection).last()
                .isNotNull()
                .isEqualTo(5);
    }

    @Test
    void checkFilteredList() {
        SimpleCollection<Integer> simpleCollection = new SimpleCollection<>(1, 1, 3, 4, 5);
        assertThat(simpleCollection).filteredOn(e -> e > 1)
                .hasSize(3)
                .first()
                .isEqualTo(3);
        assertThat(simpleCollection).filteredOnAssertions(e -> assertThat(e).isLessThan(3))
                .hasSize(2)
                .first()
                .isEqualTo(1);
    }

    @Test
    void assertMap() {
        Map<Integer, String> map = Map.of(
                1, "1", 2, "2", 3, "3");
        assertThat(map).hasSize(3)
                .containsKeys(1, 3, 2)
                .containsValues("1", "2", "3")
                .containsEntry(1, "1");
    }
}