package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SimpleConvertTest {

    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "ten", "three")
                .doesNotContain("ten");
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList(
                "first",
                "second",
                "three",
                "four",
                "five"
        );
        assertThat(list).hasSize(5)
                .contains("second", "first")
                .containsSequence("three", "four", "five")
                .contains("first", Index.atIndex(0))
                .doesNotContain("ten")
                .containsAnyOf("h", "k", "second")
                .filteredOn(e -> e.length() > 4)
                .hasSize(3)
                .first()
                .isEqualTo("first");

    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet(
                "first",
                "second",
                "three",
                "four",
                "five"
        );
        assertThat(set).hasSize(5)
                .allSatisfy(e -> {
                    assertThat(e.length()).isLessThan(7);
                    assertThat(e.length()).isGreaterThan(3);
                })
                .containsAnyOf("five", "zero", "Arnold")
                .filteredOn(e -> e.length() <= 4)
                .hasSize(2);
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap(
                "first",
                "second",
                "third",
                "fourth",
                "fifth"

        );
        assertThat(map).hasSize(5)
                .containsEntry("first", 0)
                .containsValues(0, 1, 2, 3, 4)
                .containsKeys("first", "second", "third", "fourth", "fifth")
                .doesNotContainKey("banana")
                .doesNotContainEntry("orange", 9)
                .doesNotContainValue(9);
    }
}