package serverJDK;

import serverJDK.client.domain.ClientController;
import serverJDK.client.ui.ClientGUI;
import serverJDK.server.domain.ServerController;
import serverJDK.server.repository.FileStorage;
import serverJDK.server.ui.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(new ServerWindow(), new FileStorage());

        new ClientController(new ClientGUI(), serverController);
        new ClientController(new ClientGUI(), serverController);
    }
}