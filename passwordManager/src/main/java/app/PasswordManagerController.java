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
    private TextField usernameInput, passwordInput, regUsername, regPassword, regPasswordRepeat;
    @FXML
    private Button loginButton, registerButton;
    @FXML
    private Pane loginPage, passwordListPage, registerPage;



    @FXML
    private void onLoginButtonClick(){
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if(username != "" && password != ""){
            System.out.println("username: " + username + " password: " + password);
            userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            if(userSession.login(username, password)){
                loginPage.setVisible(false);
                passwordListPage.setVisible(true);
            }else{
                userSession = null;
            }
        }

        
    }

    @FXML
    private void onRegisterButtonClick(){
        loginPage.setVisible(false);
        registerPage.setVisible(true);
    }

    @FXML
    private void onCreateUserButtonClick(){
        System.out.println("here");
        if(regPassword.getText().equals(regPasswordRepeat.getText())){
            System.out.println("here2");
            userSession = new UserSession(new CSVDatabaseTalker("src/main/resources/app/Users.csv"));
            if(userSession.registerUser(regUsername.getText(), regPassword.getText())){
                System.out.println("here3");
                loginPage.setVisible(true);
                registerPage.setVisible(false);
            }
        }
    }
}