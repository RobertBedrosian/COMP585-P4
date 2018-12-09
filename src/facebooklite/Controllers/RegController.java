package facebooklite.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import facebooklite.UserDao;
import facebooklite.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegController {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField age;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Label registrationSuccessful;

    @FXML
    private Label userNameTaken;

    ArrayList<TextField> errors  = new ArrayList<TextField>();

    @FXML
    private void registerUser() throws SQLException {

        boolean fNameProvided = false;
        boolean lNameProvided = false;
        boolean ageProvided = false;
        boolean emailProvided = false;
        boolean unameProvided = false;
        boolean passwordProvided = false;

        if(containsSpaceOrIsEmpty(firstName.getText())){
            firstName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            fNameProvided = false;
        }
        else{
            fNameProvided = true;
            firstName.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(lastName.getText())){
            lastName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            lNameProvided = false;
        }
        else{
            lNameProvided = true;
            lastName.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(age.getText())){
            age.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            ageProvided = false;
        }
        else{
            ageProvided = true;
            age.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(userEmail.getText())){
            userEmail.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            emailProvided = false;
        }
        else{
            emailProvided = true;
            userEmail.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(userName.getText())){
            userName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            unameProvided = false;
        }
        else{
            unameProvided = true;
            userName.setStyle(null);
        }
        if(containsSpaceOrIsEmpty(password.getText())){
            password.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            passwordProvided = false;
        }
        else{
            passwordProvided = true;
            password.setStyle(null);
        }

        if(fNameProvided && lNameProvided && ageProvided && emailProvided && unameProvided && passwordProvided){
            int userAge = 0;

            /**Check if user name is Unique (since it is our primary key)*/
            if (UserDao.userExists(userName.getText())){
                userName.setPromptText("User name already exists");
//                userName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");

                errors.add(userName);

                userNameTaken.setVisible(true);
            }else{
                userNameTaken.setVisible(false);
                System.out.println("User does not exist. User will be added");

                //Check if the user entered a number for an age.
                try{
                    userAge = Integer.parseInt(age.getText());
                }
                catch (NumberFormatException e){
//                    age.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                    errors.add(age);
                }

                //Check if the user entered a number between 13 and 120.
                if (userAge >= 13 && userAge <= 120){
                    age.setStyle(null);

                    if( isEmail(userEmail.getText()) ){
                        //Here if the email has correct syntax, age is between the age of 13 to 120, and the username is unique
                        User user = new User(firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()), userEmail.getText(), userName.getText(), password.getText());
                        UserDao.createUser(user);

                        registrationSuccessful.setVisible(true);
                        System.out.println("User added successfully!");
                    }else{
                        //here if the user did not provide a valid email
//                        userEmail.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                        errors.add(userEmail);
                    }
                }
                else{
                    //here if the user did not enter an age between 13 and 120
                    createAlertBox("Enter an age between 13 and 120.");
//                    age.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                    errors.add(age);
                }
            }
        }

        for(int j = 0; j < errors.size(); j++){
            errors.get(j).setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
        errors.clear();
    }

    private boolean isEmail(String text) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(text);

        if(m.find()){
            return true;
        }

        return false;
    }

    public void openMainFXML(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent mainFXMLParent = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene mainFXMLScene = new Scene(mainFXMLParent);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainFXMLScene);
        window.show();
    }

    private boolean containsSpaceOrIsEmpty(String text) {
        if(text.contains(" ") || text.equals("")){
            return true;
        }
        return false;
    }

    private void createAlertBox(String message){
        Alert alertBox = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alertBox.showAndWait();
    }

}
