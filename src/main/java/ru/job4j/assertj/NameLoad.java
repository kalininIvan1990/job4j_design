package ru.job4j.assertj;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NameLoad {
    private final Map<String, String> values = new HashMap<>();

    public void parse(String... names) {
        if (names.length == 0) {
            throw new IllegalArgumentException("Names array is empty");
        }
        values.putAll(Stream.of(names)
                .map(String::trim)
                .filter(this::validate)
                .map(line -> line.split("=", 2))
                .collect(Collectors.toMap(
                        e -> e[0],
                        e -> e[1],
                        "%s+%s"::formatted
                )));
    }

    private boolean validate(String name) {
        if (!name.contains("=")) {
            throw new IllegalArgumentException(
                    "this name: %s does not contain the symbol '='".formatted(name));
        }
        if (name.startsWith("=")) {
            throw new IllegalArgumentException(
                    "this name : %s does not contain a key".formatted(name));
        }
        if (name.indexOf('=') == name.length() - 1) {
            throw new IllegalArgumentException(
                    "this name: %s does not contain a value".formatted(name));
        }
        return true;
    }

    public Map<String, String> getMap() {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("collection contains no data");
        }
        return values;
    }
}
