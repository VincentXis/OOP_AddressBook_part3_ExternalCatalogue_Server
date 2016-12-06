package multipleServers;

import server.Server;

public class ServerTwo {
    public static void main(String[] args) {
        System.out.println("server start");
        new Server(1618, "contact_data2.csv");
        System.out.println("server end");
    }
}
