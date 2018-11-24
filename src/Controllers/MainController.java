package Controllers;

import dbutil.DBUtil;
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
    private void openRegFXML(ActionEvent event) throws IOException {
        Parent regFXMLParent = FXMLLoader.load(getClass().getResource("/reg.fxml"));
        Scene regFXMLScene = new Scene(regFXMLParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(regFXMLScene);
        window.show();
    }

    @FXML
    private void initialize() throws SQLException {
        System.out.println("In MainController initialize()");
        DBUtil.dbExecuteUpdate("CREATE DATABASE IF NOT EXISTS Comp585Project4");
        DBUtil.dbExecuteUpdate("CREATE TABLE IF NOT EXISTS Users(\n" +
             "FirstName varchar(255),LastName varchar(255), Age int, UserName varchar(255), Salt BLOB, HashedPassword BLOB, PRIMARY KEY(UserName))");

    }


}
