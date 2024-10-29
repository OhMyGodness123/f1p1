package com.example.f1p1;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LogField;

    @FXML
    private Button LogInBut;

    @FXML
    private PasswordField PassField;

    @FXML
    private Button SignUpBut;

    @FXML
    void initialize() {
        LogInBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String loginText = LogField.getText().trim();
                String passText = PassField.getText().trim();
                if (!loginText.isEmpty() && !passText.isEmpty()) loginUser(loginText, passText);
                else System.out.println("nichego net");

            }

            
        });
        SignUpBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SignUpBut.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/f1p1/SignUpWin.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Sign Up");
                Image icon = new Image(HelloApplication.class.getResourceAsStream("/com/example/f1p1/IconImage.jpg"));
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.show();
            }
        });
    }

    private void loginUser(String loginText, String passText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(passText);
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
            LogInBut.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/f1p1/MainChatWin.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            Image icon = new Image(HelloApplication.class.getResourceAsStream("/com/example/f1p1/IconImage.jpg"));
            stage.getIcons().add(icon);
            MainWinController mainwincon = loader.getController();
            mainwincon.setUser(user.getUserName(), user.getPassword());
            stage.setTitle("Chat Here! Hello " + user.getUserName());
            stage.show();
            stage.setOnCloseRequest(event -> {
                // Выполнение действий при закрытии окна
                System.out.println("Окно закрывается");

                // Завершаем приложение

                Platform.exit();
                System.exit(0);
            });
        }
        else {
            Shake userLoginAnim = new Shake(LogField);
            Shake userPassAnim = new Shake(PassField);
            userLoginAnim.playanim();
            userPassAnim.playanim();
        }

    }

}
