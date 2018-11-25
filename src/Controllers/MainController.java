package Controllers;

import facebooklite.MainControllerDao;
import facebooklite.Password;
import facebooklite.User;
import facebooklite.UserDao;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    @FXML
    private TextField uname;

    @FXML
    private TextField password;

    @FXML
    private void initialize() throws SQLException {
        System.out.println("In MainController initialize()");
        MainControllerDao.createDB("Comp585Project4");
        MainControllerDao.createTable("CREATE TABLE IF NOT EXISTS Users(\n" +
             "FirstName varchar(255),LastName varchar(255), Age int, UserName varchar(255), Salt BLOB, HashedPassword BLOB, PRIMARY KEY(UserName))");

    }

    @FXML
    private void openRegFXML(ActionEvent event) throws IOException {
        Parent regFXMLParent = FXMLLoader.load(getClass().getResource("/reg.fxml"));
        Scene regFXMLScene = new Scene(regFXMLParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(regFXMLScene);
        window.show();
    }

    @FXML
    private void login(ActionEvent event) throws SQLException, IOException {
        if (uname.getText().equals("")){

        }

        if(password.getText().equals("")) {

        }

        if(!uname.getText().equals("") && !password.getText().equals("")){
            uname.setStyle(null);
            password.setStyle(null);

            /**Check if the credentials are correct. */
            User user = UserDao.getUser(uname.getText());

            if (user != null){
                /**We are here if the user trying to login entered a correct username.*/
                Password pass = new Password();
                boolean tmpBoolean = pass.matchesStoredHashedPassword(password.getText(), user.getSalt(), user.getHashedPassword());
                if (tmpBoolean){
                    /**We are here if the user provided a correct userName && Password*/
                    System.out.println("Successfully logged in");

                    /**Switch scenes--to the dashboard-- if correct credentials*/
                    Parent regFXMLParent = FXMLLoader.load(getClass().getResource("/dashBoard.fxml"));
                    Scene regFXMLScene = new Scene(regFXMLParent);
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(regFXMLScene);
                    window.show();

                }else{
                    System.out.println("Incorrect username and/or Password");
                }
            }
            else{
                System.out.println("Incorrect user name");
            }
        }
        else{
            if(uname.getText().equals("")){
                uname.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }

            if(password.getText().equals("")){
                password.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        }
    }

    @FXML
    private void openResetOneFXML(ActionEvent event) throws IOException {
        Parent regFXMLParent = FXMLLoader.load(getClass().getResource("/resetOne.fxml"));
        Scene regFXMLScene = new Scene(regFXMLParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(regFXMLScene);
        window.show();
    }

}
