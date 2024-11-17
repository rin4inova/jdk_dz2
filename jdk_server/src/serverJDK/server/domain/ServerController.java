package serverJDK.server.domain;

import serverJDK.client.domain.ClientController;
import serverJDK.server.repository.Repository;
import serverJDK.server.ui.ServerView;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean isRunning;
    private ServerView serverView;
    private List<ClientController> clientControllers;
    private Repository<String> repository;

    public ServerController(ServerView serverView, Repository<String> repository) {
        this.serverView = serverView;
        this.repository = repository;
        this.clientControllers = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void start() {
        if (isRunning) {
            displayMessage("Сервер уже ранее был запущен");
        } else {
            isRunning = true;
            displayMessage("Сервер запущен");
        }
    }

    public void stop() {
        if (!isRunning) {
            displayMessage("Сервер уже ранее был остановлен");
        } else {
            isRunning = false;
            while (!clientControllers.isEmpty()) {
                disconnectClient(clientControllers.get(clientControllers.size() - 1));
            }
            displayMessage("Сервер остановлен");
        }
    }

    public void disconnectClient(ClientController clientController) {
        clientControllers.remove(clientController);
        if (clientController != null) {
            clientController.disconnectedFromServer();
            displayMessage(clientController.getName() + " вышел из чата");
        }
    }

    public boolean connectClient(ClientController clientController) {
        if (!isRunning) {
            return false;
        }
        clientControllers.add(clientController);
        displayMessage(clientController.getName() + " подключился к чату");
        return true;
    }

    public void broadcastMessage(String message) {
        if (!isRunning) {
            return;
        }
        displayMessage(message);
        sendMessageToAllClients(message);
        saveMessageToHistory(message);
    }

    private void sendMessageToAllClients(String message) {
        for (ClientController clientController : clientControllers) {
            clientController.answerFromServer(message);
        }
    }

    private void saveMessageToHistory(String message) {
        repository.save(message);
    }

    private void displayMessage(String message) {
        serverView.showMessage(message + "\n");
    }

    public String getHistory() {
        return repository.load();
    }
}