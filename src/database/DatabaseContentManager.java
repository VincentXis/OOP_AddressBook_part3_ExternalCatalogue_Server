package database;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class DatabaseContentManager {
    private File contactData = new File("contact_data.csv");

    public void readFromFile(PrintWriter out, Socket clientSocket) {
        String filePath = "src\\database\\";
        try (Scanner sc = new Scanner(new FileReader(filePath + contactData))) {
            while (sc.hasNextLine()) {
                out.println(sc.nextLine().replaceAll(",", " "));
            }
            clientSocket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
