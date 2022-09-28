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

import java.util.ArrayList;

public class PasswordManagerController{

    private Stage stage;
    private Scene scene;
    private Parent root;

    protected void switchScene(ActionEvent event, String sceneName) throws IOException {
        root = FXMLLoader.load(getClass().getResource(sceneName));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        String title = sceneName.substring(0, sceneName.length() - 5);
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        stage.setTitle("Password Manager | " + title);
        stage.show();
    }
}