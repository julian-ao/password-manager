package app;

import app.database.CSVDatabaseTalker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

import java.util.ArrayList;

public class PasswordManagerController{

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    protected void switchScene(ActionEvent event, String sceneName) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(sceneName));
        scene = new Scene(root);
        stage.setScene(scene);
        String title = sceneName.substring(0, sceneName.length() - 5);
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        stage.setTitle("Password Manager | " + title);
        stage.show();
    }

    protected void rotateNode(Node element, boolean clockwise) {
        element.setRotate(0);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(100));
        if (clockwise) {
            rotateTransition.setByAngle(5);
        } else {
            rotateTransition.setByAngle(-5);
        }
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setNode(element);
        rotateTransition.play();
    }

    protected void setBorderRed(Node element) {
        element.setStyle("-fx-border-color: #FE8383");
    protected void updateUserData(){

    }
}