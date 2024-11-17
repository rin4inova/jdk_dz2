package serverJDK.client.domain;

import serverJDK.client.ui.ClientView;
import serverJDK.server.domain.ServerController;

public class ClientController {
    private boolean isConnected;
    private String name;
    private ClientView clientView;
    private ServerController serverController;

    public ClientController(ClientView clientView, ServerController serverController) {
        this.clientView = clientView;
        this.serverController = serverController;
        clientView.setClientController(this);
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (serverController.connectClient(this)) {
            displayMessage("Вы подключились к серверу\n");
            isConnected = true;
            String log = serverController.getHistory();
            if (log != null) {
                displayMessage(log);
            }
            return true;
        } else {
            displayMessage("Вам подключиться не удалось");
            return false;
        }
    }

    public void answerFromServer(String message) {
        displayMessage(message);
    }

    public void disconnectedFromServer() {
        if (isConnected) {
            isConnected = false;
            clientView.disconnectedFromServer();
            displayMessage("Вас отключили от сервера");
        }
    }

    public void disconnectServer() {
        serverController.disconnectClient(this);
    }

    public void sendMessage(String message) {
        if (isConnected) {
            if (!message.isEmpty()) {
                serverController.broadcastMessage(name + ": " + message);
            }
        } else {
            displayMessage("Отсутствует подключение к серверу");
        }
    }

    public String getName() {
        return name;
    }

    private void displayMessage(String message) {
        clientView.showMessage(message + "\n");
    }
}