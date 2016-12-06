package multipleServers;

import server.Server;

public class ServerThree {
    public static void main(String[] args) {
        System.out.println("server start");
        new Server(61619, "contact_data3.csv");
        System.out.println("server end");
    }
}
