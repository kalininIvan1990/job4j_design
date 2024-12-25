package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        List<String> phrases = readPhrases();
        List<String> log = new ArrayList<>();
        boolean isRunning = true;
        boolean isPaused = false;

        while (isRunning) {
            String userInput = scanner.nextLine();
            log.add("Пользователь: " + userInput);

            if (userInput.equalsIgnoreCase(OUT)) {
                saveLog(log);
                System.out.println("Чат завершен");
                isRunning = false;

            } else if (userInput.equalsIgnoreCase(STOP)) {
                System.out.println("стоп");
                log.add("Бот остановлен");
                isPaused = true;

            } else if (userInput.equalsIgnoreCase(CONTINUE)) {
                isPaused = false;
                log.add("Бот: Продолжаю");

            } else if (!isPaused) {
                String phraseBot = randomPhrase(phrases);
                log.add("Бот: " + phraseBot);
                System.out.println(phraseBot);
            }
        }
    }

    private List<String> readPhrases() {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(botAnswers, StandardCharsets.UTF_8))) {
            reader.lines()
                    .map(line -> line + System.lineSeparator())
                    .forEach(list::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8, true))) {
            log.forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String randomPhrase(List<String> phrases) {
        Random random = new Random();
        int index = random.nextInt(phrases.size());
        return phrases.get(index);
    }

    public static void main(String[] args) {
        ConsoleChat consoleChat = new ConsoleChat("./src/data/log.txt", "./src/data/phrases.txt");
        consoleChat.run();

    }
}
