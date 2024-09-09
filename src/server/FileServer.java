package server;

import java.io.*;
import java.net.*;

public class FileServer {
    private static final int PORT = 8084;

    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("File server is listening on port " + PORT);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 OutputStream socketOutputStream = socket.getOutputStream()) {

                String fileName = input.readLine();
                File file = new File(fileName);

                if (file.exists()) {
                    String fileExtension = fileName.substring(fileName.lastIndexOf("."));

                    PrintWriter output = new PrintWriter(socketOutputStream, true);
                    output.println(fileExtension);

                    try (FileInputStream fileInputStream = new FileInputStream(file)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                            socketOutputStream.write(buffer, 0, bytesRead);
                        }
                        socketOutputStream.flush();
                        System.out.println("File sent successfully");
                    }
                } else {
                    System.out.println("Requested file does not exist.");
                    PrintWriter output = new PrintWriter(socketOutputStream, true);
                    output.println("ERROR: File not found");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
