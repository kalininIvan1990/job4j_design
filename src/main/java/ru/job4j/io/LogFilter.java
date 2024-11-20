package ru.job4j.io;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines()
                    .filter(line -> {
                        String[] substring = line.split(" ");
                        return substring[substring.length - 2].equals("404");
                    })
                    .toList();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void saveTo(String out) {
        var data = filter();
        try (PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter(out)))) {
            for (String string : data) {
                print.println(string);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter("data/log.txt");
        logFilter.filter().forEach(System.out::println);
        logFilter.saveTo("data/404.txt");
    }
}
