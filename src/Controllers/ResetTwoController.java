package Controllers;

import facebooklite.Password;
import facebooklite.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import facebooklite.UserDao;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResetTwoController implements Initializable {

    @FXML
    private String username;

    @FXML
    private Label failureLabel;

    @FXML
    private TextField password;

    @FXML
    private Button resetButton;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    private void createNewPassword(ActionEvent event){
        if(password.getText().equals("")){
            password.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            System.out.println("Empty Password");
            failureLabel.setVisible(true);
        }else{

            Password pass = new Password(password.getText());
            UserDao.updateUserSaltAndPass(username, pass.getSalt(), pass.getHashedPassword());

            Stage stage = (Stage) resetButton.getScene().getWindow();
            stage.close();

            System.out.println("Password Changed!");
        }
    }

    public void setUsername(String username){
        this.username = username;
    }

}
