package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ModelTest {

    @Test
    void checkBoolean() {
        Model model = new Model(1, 5.255d, "name", true);
        boolean result = model.isCondition();
        assertThat(result).isTrue();
    }

    @Test
    void checkStringChain() {
        Model model = new Model(1, 5.255d, "I am learning Java", true);
        String result = model.getLine();
        assertThat(result).isNotNull()
                .isNotEmpty()
                .isNotBlank()
                .contains("am", "I")
                .doesNotContain("hdhdh")
                .startsWith("I am")
                .endsWith("Java");
    }

    @Test
    void checkInt() {
        Model model = new Model(2, 5.2d, "name", true);
        int result = model.getTop();
        assertThat(result).isNotNull()
                .isLessThan(3)
                .isGreaterThan(1)
                .isEven()
                .isPositive()
                .isEqualTo(2);
    }
}