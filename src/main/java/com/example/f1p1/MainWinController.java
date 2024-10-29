package com.example.f1p1;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainWinController {
    private ChatClient client;
    private String username;

    public void setUser(String userName, String pass){
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(pass);
        ResultSet ress = dbHandler.getUser(user);
        int counter = 0;
        while(true) {
            try {
                if (!ress.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        if (counter >= 1){
            this.username = user.getUserName();
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea ChatText;

    @FXML
    private TextField MsgText;

    @FXML
    private Button SendTextBut;

    @FXML
    void initialize() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        SendTextBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!MsgText.getText().equals("")){
                client.sendMessage("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "]" +
                        " <" + username + "> " + MsgText.getText());
                MsgText.setText("");}
            }
        });
        try {
            this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
    private void onMessageReceived(String message) {
        ChatText.setText(ChatText.getText() + message + "\n");
    }

}
