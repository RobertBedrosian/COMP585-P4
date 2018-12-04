package facebooklite.Controllers;

import facebooklite.DBUtil;
import facebooklite.User;
import facebooklite.UserDao;
import javafx.fxml.FXML;
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
            UserDao.updateStatus(user, newStatusText.getText());
        }
        catch (SQLException e) {
            System.out.println("Failed to update status");
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    @FXML
    public void close() {
        Stage stage = (Stage) newStatusText.getScene().getWindow();
        stage.close();
    }
}
