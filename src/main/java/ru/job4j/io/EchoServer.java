package ru.job4j.io;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    String requestLine = in.readLine();
                    String[] parts = requestLine.split("[?= ]");
                    if (parts.length > 3 && "msg".equals(parts[2])) {
                        String message = parts[3];
                        switch (message) {
                            case "Hello":
                                out.write("Hello".getBytes());
                                break;
                            case "Exit":
                                out.write("Server has stopped".getBytes());
                                server.close();
                                break;
                            default:
                                out.write(message.getBytes());
                        }

                    } else {
                        out.write("Invalid key".getBytes());
                    }
                    out.flush();
                }
            }
        }
    }
}
