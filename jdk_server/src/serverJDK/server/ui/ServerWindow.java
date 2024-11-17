package serverJDK.server.ui;

import serverJDK.server.domain.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements ServerView {

    private static final int WINDOW_X_POSITION = 500;
    private static final int WINDOW_Y_POSITION = 550;
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 500;

    private JButton startButton, stopButton;
    private JTextArea logArea;

    private ServerController serverController;

    public ServerWindow(){
        initializeSettings();
        createUIComponents();
        setVisible(true);
    }

    @Override
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    private void initializeSettings() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_X_POSITION, WINDOW_Y_POSITION);
        setResizable(false);
        setTitle("Chat server");
    }

    private void createUIComponents() {
        logArea = new JTextArea();
        add(logArea);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        startButton.addActionListener(e -> serverController.start());
        stopButton.addActionListener(e -> serverController.stop());

        panel.add(startButton);
        panel.add(stopButton);
        return panel;
    }

    @Override
    public void showMessage(String message) {
        logArea.append(message + "\n");
    }
}
