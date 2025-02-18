package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class CSVReader {
    public static void handle(ArgsName argsName) {
        String path = argsName.get("path");
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        String[] filters = argsName.get("filter").split(",");

        try (Scanner scanner = new Scanner(new FileReader(path))) {
            List<Integer> indexes = new ArrayList<>();
            List<String> result = new ArrayList<>();

            if (scanner.hasNextLine()) {
                String headerLine = scanner.nextLine();
                String[] headers = headerLine.split(delimiter);

                for (String filter : filters) {
                    for (int i = 0; i < headers.length; i++) {
                        if (headers[i].equals(filter)) {
                            indexes.add(i);
                            break;
                        }
                    }
                }
                StringJoiner headerJoiner = new StringJoiner(delimiter);
                for (int index : indexes) {
                    headerJoiner.add(headers[index]);
                }
                result.add(headerJoiner.toString());
            }

            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(delimiter);
                StringJoiner valueJoiner = new StringJoiner(delimiter);

                for (int index : indexes) {
                    if (index < values.length) {
                        valueJoiner.add(values[index]);
                    }
                }
                result.add(valueJoiner.toString());
            }

            if ("stdout".equals(out)) {
                result.forEach(System.out::println);
            } else {
                try (PrintWriter writer = new PrintWriter(new FileWriter(out))) {
                    for (String line : result) {
                        writer.println(line);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            throw new IllegalArgumentException("Arguments not found");
        }
        ArgsName argsName = ArgsName.of(args);
        handle(argsName);
    }
}
