package multipleServers;

import server.Server;

public class ServerOne {
    public static void main(String[] args) {
        System.out.println("server start");
        new Server(6117, "contact_data1.csv");
        System.out.println("server end");
    }
}
