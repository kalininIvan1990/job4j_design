package ru.job4j.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.*;

class AnalysisTest {
    @Test
    void unavailable(@TempDir Path tempDir) throws IOException {
        File source = tempDir.resolve("server.log").toFile();
        File target = tempDir.resolve("target.csv").toFile();
        StringJoiner result = new StringJoiner("\n");

        try (PrintWriter output = new PrintWriter(source)) {
            output.println("""
                    200 10:56:01
                    500 10:57:01
                    400 10:58:01
                    300 10:59:01
                    500 11:01:02
                    200 11:02:02""");
        }
        Analysis analysis = new Analysis();
        analysis.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        try (BufferedReader input = new BufferedReader(new FileReader(target))) {
            input.lines().forEach(result::add);
        }
        assertThat("10:57:01;10:59:01;\n11:01:02;11:02:02;").isEqualTo(result.toString());
    }

}