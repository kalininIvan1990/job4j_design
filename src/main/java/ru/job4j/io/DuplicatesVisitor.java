package ru.job4j.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final Map<FileProperty, List<Path>> map = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProperty = new FileProperty(attrs.size(), file.getFileName().toString());
        map.computeIfAbsent(fileProperty, key -> new ArrayList<>()).add(file);
        return super.visitFile(file, attrs);
    }

    public void printFile() {
        for (Map.Entry<FileProperty, List<Path>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                for (Path path : entry.getValue()) {
                    System.out.println(path.toAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Path start = Path.of("./data");
        DuplicatesVisitor duplicatesVisitor = new DuplicatesVisitor();
        Files.walkFileTree(start, duplicatesVisitor);
        duplicatesVisitor.printFile();
    }
}
