package server;

import database.DatabaseContentManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private DatabaseContentManager dcm;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server() {
        dcm = new DatabaseContentManager();
        startServer();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(61616);
            while (true) {
                clientSocket = serverSocket.accept();
                openStreamsForClientServerCommunication(clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openStreamsForClientServerCommunication(Socket clientSocket) {
        new Thread(() -> {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
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
                dcm.readFromFile(out);
                out.println();
            } else if (clientInputRequest.equals("exit")) {
                break;
            }
        }
    }
}
