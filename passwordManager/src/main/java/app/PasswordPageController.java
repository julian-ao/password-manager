package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PasswordPageController extends PasswordManagerController {
    @FXML
    private FlowPane passwordList;

    @FXML
    private Pane passwordListPage;

    private UserSession userSession;



    @FXML
    public void initialize(){
    }
    // PASSWORDS PAGE METHODS

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
