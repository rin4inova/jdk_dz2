package serverJDK.server.ui;

import serverJDK.server.domain.ServerController;

public interface ServerView {
    void showMessage(String message);
    void setServerController(ServerController serverController);
}
