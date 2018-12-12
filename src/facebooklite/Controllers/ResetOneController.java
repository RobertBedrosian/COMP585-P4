package facebooklite.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import facebooklite.UserDao;
import facebooklite.User;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ResetOneController {

    @FXML
    private TextField userName;

    @FXML
    private TextField userEmail;

    @FXML
    private Button resetPassword;

    @FXML
    private Label failureNotice;

    @FXML
    private Label successNotice;

    @FXML
    private AnchorPane resetOneFrame;

    @FXML
    private void resetPassword() throws SQLException, IOException {
        successNotice.setVisible(false);
        failureNotice.setVisible(false);

        if(!userName.getText().equals("") || !userEmail.getText().equals("")){
            User user = UserDao.getUser(userName.getText());

            if(user != null){
                /**If the user name exists we are here*/

                if(user.getEmail().equals(userEmail.getText())){
                    /**Change to scene two*/

                    resetOneFrame.setDisable(true);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/resetTwo.fxml"));
                    loader.load();
                    ResetTwoController temp = loader.getController();
                    temp.setUsername(userName.getText());

                    Parent p = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.showAndWait();

                    resetOneFrame.setDisable(false);


                    /**Check if passwords were changed*/
                    User maybeUpdatedUser = UserDao.getUser(userName.getText());

                    byte[] originalHashCode = user.getHashedPassword();
                    byte [] maybeNewHashCode = maybeUpdatedUser.getHashedPassword();

                    for(int i = 0; i < originalHashCode.length; i++){
                        if(originalHashCode[i] != maybeNewHashCode[i]){
                            successNotice.setVisible(true);
                            break;
                        }
                        else{
                            /**If the hashes are equal its because no new password was made*/
                            //DO NOTHING
                        }
                    }


                }
                else{
                    System.out.println("Wrong credentials at resetOne");
                    failureNotice.setVisible(true);
                }
            }
            else{
                /**We are here if entered userName does not exist*/
            }
        }
    }

    @FXML
    private void openMainFXML() throws IOException {
        Parent mainFXMLParent = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene mainFXMLScene = new Scene(mainFXMLParent);
        Stage window = (Stage) userEmail.getScene().getWindow();
        window.setScene(mainFXMLScene);
        window.show();
    }
    @FXML
    private void resethandle(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            try{
                resetPassword();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void backhandle(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            try{
                openMainFXML();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
