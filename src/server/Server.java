package server;

import database.DatabaseContentManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private DatabaseContentManager dcm;
    ServerSocket serverSocket;
    Socket clientSocket;

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

                dcm.readFromFile(out, clientSocket);

//                out.notify();
            } else if (clientInputRequest.equals("exit")) {
                break;
            }
        }
    }

    private void readFromFile(PrintWriter out) {
        try (Scanner sc = new Scanner(new FileReader("src\\database\\contact_data.csv"))) {
            while (sc.hasNextLine()) {
                out.println(sc.nextLine().replaceAll(",", " "));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
