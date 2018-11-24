package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import facebooklite.UserDao;
import facebooklite.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegController {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField age;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private void registerUser() throws SQLException {
        if(containsSpaceOrIsEmpty(firstName.getText())){
            firstName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
        if(containsSpaceOrIsEmpty(lastName.getText())){
            lastName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
        if(containsSpaceOrIsEmpty(age.getText())){
            age.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
        if(containsSpaceOrIsEmpty(userName.getText())){
            userName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
        if(containsSpaceOrIsEmpty(password.getText())){
            password.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }


        /**Check if user name is Unique (since it is our primary key)*/
        if ( UserDao.userExists(userName.getText())){
            System.out.println("User exists");
            userName.setPromptText("User name already exists");
            userName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }else{
            System.out.println("User does not exist. User will be added");
            User user = new User(firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()), userName.getText(), password.getText());
            UserDao.insertUser(user);
            UserDao.updateUserSaltAndPass(userName.getText(), user.getSalt(), user.getHashedPassword());
            System.out.println("User added successfully!");
        }

    }

    private boolean containsSpaceOrIsEmpty(String text) {
        if(text.contains(" ") || text.equals("")){
            return true;
        }
        return false;
    }
}
