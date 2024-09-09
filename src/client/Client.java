package client;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 8081;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server");

            while (true) {
                if (input.ready()) {
                    String serverMessage = input.readLine();
                    if (serverMessage == null) {
                        break;
                    }
                    System.out.println("Server: " + serverMessage);
                }
                if (userInput.ready()) {
                    String clientMessage = userInput.readLine();
                    output.println(clientMessage);
                    System.out.println("Sent: " + clientMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
