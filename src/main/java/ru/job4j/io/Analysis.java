package ru.job4j.io;

import java.io.*;
import java.util.StringJoiner;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
        BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
            String line = "";
            String start = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int status = Integer.parseInt(parts[0]);
                String time = parts[1];
                if (start == null && (status == 400 || status == 500)) {
                    start = time;
                } else if (start != null && (status == 200 || status == 300)) {
                    writer.write(start + ";" + time + ";" + System.lineSeparator());
                    start = null;
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");

    }
}
