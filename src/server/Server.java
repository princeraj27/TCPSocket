package server;

import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8081;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept();
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Client connected");


            while (true) {
                if (input.ready()) {
                    String clientMessage = input.readLine();
                    if (clientMessage == null) {
                        break;
                    }
                    System.out.println("Received: " + clientMessage);
                    output.println("Server received: " + clientMessage);
                }

                if (userInput.ready()) {
                    String serverMessage = userInput.readLine();
                    output.println(serverMessage);
                    System.out.println("Sent: " + serverMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
