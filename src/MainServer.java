import server.Server;

public class MainServer {
    /**
    contact_data.csv contains all the data combined that each of the others have.
     */
    public static void main(String[] args) {
        System.out.println("server start");
        new Server(61616, "contact_data.csv");
        System.out.println("server end");
    }
}
