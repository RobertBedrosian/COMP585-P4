package facebooklite.Controllers;

import facebooklite.DBUtil;
import facebooklite.User;
import facebooklite.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModifyStatusController {
    private User user;

    @FXML
    TextArea newStatusText;

    public ModifyStatusController(User user) {
        this.user = user;
    }

    @FXML
    public void accept() {
        try {
            if (newStatusText.getText().replace("\n", " ").length() > 50){
                createAlertBox("Please enter a status with 50 characters or less");

            }
            else{
                user.setStatus(newStatusText.getText().replace("\n", " "));
                UserDao.updateStatus(user);
                close();
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to update status");
            e.printStackTrace();
        }
    }
    private void createAlertBox(String message){
        Alert alertBox = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alertBox.showAndWait();
    }

    @FXML
    public void close() {
        Stage stage = (Stage) newStatusText.getScene().getWindow();
        stage.close();
    }
}
