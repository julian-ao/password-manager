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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.*;

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
        updatePasswords();
    }


    private void updatePasswords(){
        passwordList.getChildren().clear();
        ArrayList<ArrayList<String>> data = userSession.getProfilesNativeTypes();
        ArrayList<GridPane> passwords = new ArrayList<GridPane>();
        for (ArrayList<String> elem : data) { // temporary dummy data
            passwords.add(passwordComponent(elem.get(0), elem.get(1), elem.get(2)));
        }
        for (GridPane i : passwords) {
            passwordList.getChildren().add(i);
        }
    }

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

    @FXML
    private void onLogOutButtonClick(ActionEvent event) throws IOException {
        // TODO
        // log out?
        // userSession.logOut()?
        super.switchScene(event, "login.fxml");
    }

    @FXML
    private void onAddPasswordButtonClick(ActionEvent event) throws IOException {


        
        addLoginOverlay.setVisible(true);
    }

    @FXML
    private void onAddLoginCloseButton(ActionEvent event) throws IOException {
        addLoginTitleTextField.setText("");
        addLoginUsernameTextField.setText("");
        addLoginPasswordTextField.setText("");


        addLoginOverlay.setVisible(false);
    }

    @FXML
    private void onAddLoginButton(ActionEvent event) throws IOException {
        userSession.insertProfile(addLoginUsernameTextField.getText(), addLoginTitleTextField.getText(), addLoginPasswordTextField.getText());

        addLoginTitleTextField.setText("");
        addLoginUsernameTextField.setText("");
        addLoginPasswordTextField.setText("");
        addLoginOverlay.setVisible(false);
        updatePasswords();
    }
}
