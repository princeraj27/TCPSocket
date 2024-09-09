package client;

import java.io.*;
import java.net.*;
import java.util.UUID;

public class FileClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 8084;

    public static void start() {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try {

            System.out.print("Enter the file name to request: ");
            String fileName = userInput.readLine();

            // Connect to the server
            try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                 InputStream socketInputStream = socket.getInputStream()) {

                System.out.println("Connected to file server");
                output.println(fileName);
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(socketInputStream));
                String fileExtension = serverInput.readLine();
                if (fileExtension.startsWith("ERROR")) {
                    System.out.println(fileExtension);
                    return;
                }


                String uniqueId = UUID.randomUUID().toString();
                String savePath = uniqueId + fileExtension;

                try (FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = socketInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("File received successfully and saved to " + savePath);
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
