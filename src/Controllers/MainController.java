package Controllers;

import facebooklite.DBUtil;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class MainController {

    @FXML
    private void initialize() throws SQLException {
        System.out.println("In MainController initialize()");
        DBUtil util = new DBUtil();
        util.dbConnect();
    }


}
