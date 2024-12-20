package ru.job4j.io;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        validate(args);
        Path start = Paths.get(args[0]);
        search(start, path -> path.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();

    }

    public static void validate(String[] args) {
        File root = new File(args[0]);
       if (args.length < 2 || !root.exists() || !root.isDirectory()) {
            throw new IllegalArgumentException(String.format("Корневая папка %s не существует", root.getAbsolutePath()));
        }
        if (!args[1].endsWith(".js")) {
            throw new IllegalArgumentException("Файл не найден");
        }
    }
}
