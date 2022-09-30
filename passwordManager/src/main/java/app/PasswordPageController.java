package app;

import app.database.*;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PasswordPageController extends PasswordManagerController {
    @FXML
    private FlowPane passwordList;

    @FXML
    private Pane passwordListPage;

    private UserSession userSession;



    @FXML
    public void initialize(){
        userSession = UserSession.getInstance();

        
        ArrayList<GridPane> passwords = new ArrayList<GridPane>();
        for(int i = 0; i < 5; i++){
            passwords.add(passwordComponent(userSession.getUsername(), "••••••••••••", "kennbr@gmail.com"));
        }

        for (GridPane i : passwords) {
            passwordList.getChildren().add(i);
        }
                 

    }

    private GridPane passwordComponent(String username, String password, String email) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefHeight(90);
        gridPane.setPrefWidth(580);

        Text usernameText = new Text(username);
        usernameText.setStyle("-fx-font: 40 system;");
        usernameText.setWrappingWidth(290);

        Text passwordText = new Text(password);
        passwordText.setStyle("-fx-font: 40 system;-fx-text-alignment: right;");
        passwordText.setWrappingWidth(290);

        Text emailText = new Text(email);
        emailText.setStyle("-fx-font: 25 system;");

        gridPane.add(usernameText, 0, 0);
        gridPane.add(passwordText, 1, 1);
        gridPane.add(emailText, 0, 1);

        return gridPane;
    }

    

    @FXML
    private void onLogOutButtonClick(ActionEvent event) throws IOException {
        // log out?
        // userSession.logOut()?
        super.switchScene(event, "login.fxml");
    }

    @FXML
    private void onAddPasswordButtonClick(ActionEvent event) throws IOException {
        
        userSession = (UserSession)((Stage)passwordListPage.getScene().getWindow()).getUserData();
        System.out.println(userSession.getUsername());
    }

}
