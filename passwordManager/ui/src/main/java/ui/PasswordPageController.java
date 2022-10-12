package ui;



import java.io.IOException;
import java.util.ArrayList;

import core.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.*; 
import javafx.scene.effect.*;

public class PasswordPageController extends PasswordManagerController {

    @FXML
    private FlowPane passwordList;

    @FXML
    private Pane passwordListPage, addLoginOverlay;

    @FXML
    private Button addLoginCloseButton;

    @FXML
    private Text signedInAsText;

    @FXML
    private TextField addLoginTitleTextField, addLoginUsernameTextField, addLoginPasswordTextField;

    @FXML
    private Rectangle addLoginBlur;

    private UserSession userSession;

    @FXML
    public void initialize() {
        userSession = UserSession.getInstance();

        signedInAsText.setText("Signed in as: " + userSession.getUsername());
        
        ArrayList<GridPane> passwords = new ArrayList<GridPane>();
        for (int i = 0; i < 5; i++) { // temporary dummy data
            passwords.add(passwordComponent("Github", "••••••••••••", "kennbr@gmail.com"));
        }
        for (GridPane i : passwords) {
            passwordList.getChildren().add(i);
        }
    }
    /**
     * passwordComponent sets up a component used in the list view
     * @param name name displayed in the password Component
     * @param password password displayed
     * @param email email displayed
     * @return A GridPane object used to place in the listview 
     */
    private GridPane passwordComponent(String name, String password, String email) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefHeight(90);
        gridPane.setPrefWidth(750);

        Text nameText = new Text(name);
        nameText.setStyle("-fx-font: 40 system;");
        nameText.setWrappingWidth(375);

        Text passwordText = new Text(password);
        passwordText.setStyle("-fx-font: 40 system;-fx-text-alignment: right;");
        passwordText.setWrappingWidth(375);

        Text emailText = new Text(email);
        emailText.setStyle("-fx-font: 25 system;");

        gridPane.add(nameText, 0, 0);
        gridPane.add(emailText, 0, 1);
        gridPane.add(passwordText, 1, 1);

        return gridPane;
    }
    /**
     * Tells the userSession to log out and switches the scene back to the login page
     * @param event ActionEvent object used in the switchScene method
     * @throws IOException if the switchScene does not work
     */
    @FXML
    private void onLogOutButtonClick(ActionEvent event) throws IOException {
        userSession.logOut();
        super.switchScene(event, "login.fxml");
    }


    @FXML
    private void onAddPasswordButtonClick(){
        addLoginOverlay.setVisible(true);
    }

    
    @FXML
    private void onAddLoginCloseButton(){
        addLoginTitleTextField.setText("");
        addLoginUsernameTextField.setText("");
        addLoginPasswordTextField.setText("");

        addLoginOverlay.setVisible(false);
    }
}
