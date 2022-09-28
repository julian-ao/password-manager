package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class PasswordPageController extends PasswordManagerController {
    @FXML
    private FlowPane passwordList;

    private UserSession userSession;


    
    // PASSWORDS PAGE METHODS

    @FXML
    private void onLogOutButtonClick(ActionEvent event) throws IOException {
        // log out?
        // userSession.logOut()?
        super.switchScene(event, "login.fxml");
    }

    @FXML
    private void onAddPasswordButtonClick(ActionEvent event) throws IOException {
        // something happens
    }

}
