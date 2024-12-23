package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path source : sources) {
                zip.putNextEntry(new ZipEntry(source.toFile().toString()));
                try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(source.toFile()))) {
                    zip.write(input.readAllBytes());
                }
                zip.closeEntry();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validate(ArgsName args) {
        Path directory = Paths.get(args.get("d"));
        String exclude = args.get("e");
        String zip = args.get("o");

        if (!directory.toFile().exists() || !directory.toFile().isDirectory()) {
            throw new IllegalArgumentException("Директория не найдена");
        }
        if (!exclude.startsWith(".")) {
            throw new IllegalArgumentException("Расширение должно начинаться с '.'");
        }
        if (!zip.endsWith(".zip")) {
            throw new IllegalArgumentException("Имя архива должно иметь расширение '.zip'");
        }
    }

    public static void main(String[] args) throws IOException {
        Zip zip = new Zip();
        ArgsName argsName = ArgsName.of(args);
        if (args.length == 0) {
            throw new IllegalArgumentException("Аргументы не переданы");
        }
        zip.validate(argsName);
        List<Path> packFiles = Search.search(Paths.get(argsName.get("d")),
                path -> !path.toFile().getName().endsWith(argsName.get("e")));
        zip.packFiles(packFiles, new File("job4j.zip"));
    }
}
