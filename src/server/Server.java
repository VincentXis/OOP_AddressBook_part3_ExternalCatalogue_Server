package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private String fileName;

    public Server(int portNumber, String fileName) {
        this.fileName = fileName;
        startServer(portNumber);
    }

    private void startServer(int portNumber) {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                openStreamsForClientServerCommunication(clientSocket);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void openStreamsForClientServerCommunication(Socket clientSocket) {
        new Thread(() -> {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                System.out.println("Client connected: " + clientSocket.getLocalAddress().getHostAddress());
                clientRequestManagement(in, out);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void clientRequestManagement(BufferedReader in, PrintWriter out) throws IOException {
        String clientInputRequest;
        while ((clientInputRequest = in.readLine()) != null) {
            System.out.println("Request: " + clientInputRequest);
            if (clientInputRequest.equals("get all")) {
                readFromFileInDatabase(out);
                out.println();
            } else if (clientInputRequest.equals("exit")) {
                break;
            }
        }
    }

    private void readFromFileInDatabase(PrintWriter out) {
        String filePath = "src\\server\\contactDatabase\\";
        try (Scanner sc = new Scanner(new FileReader(filePath + new File(this.fileName)))) {
            while (sc.hasNextLine()) {
                out.println(sc.nextLine().replaceAll(",", " "));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
