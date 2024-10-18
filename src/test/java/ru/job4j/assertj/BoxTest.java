package ru.job4j.assertj;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 3);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .startsWith("T")
                .isNotBlank()
                .isNotEmpty();
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 12);
        String name = box.whatsThis();
        assertThat(name).startsWith("Cu")
                .isNotEmpty()
                .endsWith("e")
                .isEqualTo("Cube");
    }

    @Test
    void whenSphereThenVertexIsNull() {
        Box box = new Box(0, 10);
        int result = box.getNumberOfVertices();
        assertThat(result).isZero()
                .isEqualTo(0);
    }

    @Test
    void whenCubeThenVertexIs8() {
        Box box = new Box(8, 12);
        int result = box.getNumberOfVertices();
        assertThat(result).isNotNull()
                .isPositive()
                .isEven()
                .isEqualTo(8);

    }

    @Test
    void whenSphereExist() {
        Box box = new Box(0, 10);
        boolean result = box.isExist();
        assertThat(result).isTrue();
    }

    @Test
    void whenUnknownIsNotExist() {
        Box box = new Box(-1, 0);
        boolean result = box.isExist();
        assertThat(result).isFalse();
    }

    @Test
    void whenEdgeSphereIs1ThenArea12Dot56() {
        Box box = new Box(0, 1);
        double result = box.getArea();
        assertThat(result).isGreaterThan(12.5)
                .isLessThan(12.6)
                .isCloseTo(12.54, withPrecision(0.2))
                .isEqualTo(12.56, withPrecision(0.01));
    }

    @Test
    void WhenEdgeCubeIs2ThenArea24() {
        Box box = new Box(8, 2);
        double result = box.getArea();
        assertThat(result).isNotNull()
                .isGreaterThan(20.0)
                .isEqualTo(24.0, withPrecision(0.001));
    }
}