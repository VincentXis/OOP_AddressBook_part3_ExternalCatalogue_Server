package database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DatabaseContentManager {
    private File contactData = new File("contact_data.csv");

    public void readFromFile(PrintWriter out) {
        String filePath = "src\\database\\";
        try (Scanner sc = new Scanner(new FileReader(filePath + contactData))) {
            while (sc.hasNextLine()) {
                out.println(sc.nextLine().replaceAll(",", " "));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
