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
            ServerSocket serverSocket;
            while (true) {
                serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                openStreamsForClientServerCommunication(clientSocket);
                serverSocket.close();
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
                readClientRequests(in, out);
                System.out.println("Client disconnected");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    /**
     * @throws IOException - because the error can be caught where it is supposed to be used.
     */
    private void readClientRequests(BufferedReader in, PrintWriter out) throws IOException {
        String clientInputRequest;
        while ((clientInputRequest = in.readLine()) != null) {
            System.out.println("Request: " + clientInputRequest);
            if (clientInputRequest.equals("getall")) {

//                readFromFileToClient(out);
                readFromFileToClientOneString(out);
                out.println();
            } else if (clientInputRequest.equals("exit")) {
                break;
            }
        }
    }

    private void readFromFileToClient(PrintWriter out) {
        String filePath = "src\\server\\contactDatabase\\";
        try (Scanner sc = new Scanner(new FileReader(filePath + new File(this.fileName)))) {
            while (sc.hasNextLine()) {
                out.println(sc.nextLine().replaceAll(",", " "));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFromFileToClientOneString(PrintWriter out) {
        String fileContents = "";
        String filePath = "src\\server\\contactDatabase\\";
        try (Scanner sc = new Scanner(new FileReader(filePath + new File(this.fileName)))) {
            while (sc.hasNextLine()) {
                fileContents +=sc.nextLine().replaceAll(",", " ") + "\n";
            }
            out.println(fileContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
