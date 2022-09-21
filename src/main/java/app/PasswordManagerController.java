package app;


import app.database.CSVDatabaseTalker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PasswordManagerController {

    UserSession userSession;

    @FXML
    private TextField usernameInput, passwordInput;
    @FXML
    private Button loginButton, registerButton;
    @FXML
    private Pane loginPage;



    @FXML
    private void onLoginButtonClick(){
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if(username != "" && password != ""){
            System.out.println("username: " + username + " password: " + password);
            userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/testUsers.csv"));
            if(userSession.login(username, password)){
                loginPage.setVisible(false);
            }else{
                userSession = null;
            }
        }

        
    }

    @FXML
    private void onRegisterButtonClick(){
        //todo
        //show the register page and hide the login page
    }
}