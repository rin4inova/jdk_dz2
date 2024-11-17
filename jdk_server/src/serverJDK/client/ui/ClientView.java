package serverJDK.client.ui;

import serverJDK.client.domain.ClientController;

public interface ClientView {

    void showMessage(String message);

    void disconnectedFromServer();

    void setClientController(ClientController clientController);
}