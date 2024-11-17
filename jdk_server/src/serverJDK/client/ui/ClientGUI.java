package serverJDK.client.ui;

import serverJDK.client.domain.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    private JTextArea logArea;
    private JTextField ipAddressField, portField, loginField, messageField;
    private JPasswordField passwordField;
    private JButton loginButton, sendButton;
    private JPanel headerPanel;

    private ClientController clientController;

    public ClientGUI() {
        initializeSettings();
        createUIComponents();
        setVisible(true);
    }

    @Override
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    private void initializeSettings() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat Client");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void showMessage(String message) {
        logArea.append(message + "\n");
    }

    @Override
    public void disconnectedFromServer() {
        hideHeaderPanel(true);
    }

    public void disconnectServer() {
        clientController.disconnectServer();
    }

    public void hideHeaderPanel(boolean visible) {
        headerPanel.setVisible(visible);
    }

    public void login() {
        if (clientController.connectToServer(loginField.getText())) {
            headerPanel.setVisible(false);
        }
    }

    private void sendMessage() {
        clientController.sendMessage(messageField.getText());
        messageField.setText("");
    }

    private void createUIComponents() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLogArea(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2, 3));
        ipAddressField = new JTextField("127.0.0.1");
        portField = new JTextField("8189");
        loginField = new JTextField("Ivan Ivanovich");
        passwordField = new JPasswordField("123456");
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());

        headerPanel.add(ipAddressField);
        headerPanel.add(portField);
        headerPanel.add(new JPanel());
        headerPanel.add(loginField);
        headerPanel.add(passwordField);
        headerPanel.add(loginButton);

        return headerPanel;
    }

    private JScrollPane createLogArea() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        return new JScrollPane(logArea);
    }

    private JPanel createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    sendMessage();
                }
            }
        });
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        panel.add(messageField);
        panel.add(sendButton, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            disconnectServer();
        }
    }
}