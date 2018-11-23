package Controllers;

import dbutil.DBUtil;
import facebooklite.User;
import facebooklite.UserDao;

import javafx.fxml.FXML;

import java.sql.SQLException;

public class MainController {

    @FXML
    private void initialize() throws SQLException {
        System.out.println("In MainController initialize()");
        DBUtil.dbExecuteUpdate("CREATE DATABASE IF NOT EXISTS Comp585Project4");
        DBUtil.dbExecuteUpdate("CREATE TABLE IF NOT EXISTS Users(\n" +
             "FirstName varchar(255),LastName varchar(255), Age int, UserName varchar(255), Salt BLOB, HashedPassword BLOB, PRIMARY KEY(UserName))");

    }


}
