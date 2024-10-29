package com.example.f1p1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

public class signUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LogField;

    @FXML
    private TextField LogField1;

    @FXML
    private TextField LogField11;

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
            public void handle(ActionEvent actionEvent) {
                signUpNewUser();
            }
        });
        SignUpBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogInBut.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/f1p1/hello-view.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.close();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                Image icon = new Image(HelloApplication.class.getResourceAsStream("/com/example/f1p1/IconImage.jpg"));
                stage.getIcons().add(icon);
                stage.setTitle("Log In");
                stage.show();
            }
        });
    }

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String firstName = LogField1.getText();
        String lastName = LogField11.getText();
        String userName = LogField.getText();
        String password = PassField.getText();
        User user = new User(firstName, lastName, userName, password);
        try {
            String ans = dbHandler.sighupUser(user);
            if (ans.equals("est")) {
                LogField.setText("username already exist");
                Shake userloganim = new Shake(LogField);
                userloganim.playanim();
                Shake userlog1anim = new Shake(LogField1);
                userlog1anim.playanim();
                Shake userlog11anim = new Shake(LogField11);
                userlog11anim.playanim();
                Shake userpassanim = new Shake(PassField);
                userpassanim.playanim();
            } else if (ans.equals("vse norm")) {
                LogInBut.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/f1p1/hello-view.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.close();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                Image icon = new Image(HelloApplication.class.getResourceAsStream("/com/example/f1p1/IconImage.jpg"));
                stage.getIcons().add(icon);
                stage.setTitle("Log In");
                stage.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
