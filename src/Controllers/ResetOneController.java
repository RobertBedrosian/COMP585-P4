package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import facebooklite.UserDao;
import facebooklite.User;

import java.sql.SQLException;

public class ResetOneController {

    @FXML
    private TextField userName;

    @FXML
    private TextField userEmail;

    @FXML
    private void resetPassword() throws SQLException {
        if(!userName.getText().equals("") || !userEmail.getText().equals("")){
            if(UserDao.userExists(userName.getText())){
                /**If the user name exists we are here*/
                
            }
            else{
                /**We are here if entered userName does not exist*/
            }
        }
    }
}
