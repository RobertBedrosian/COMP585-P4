package facebooklite.Controllers;

import facebooklite.User;
import facebooklite.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SettingsController {
    private User user;

    @FXML
    CheckBox hideAge;
    @FXML
    CheckBox hidePosts;
    @FXML
    CheckBox hideStatus;
    @FXML
    CheckBox hideFriends;

    public SettingsController(User user) {
        this.user = user;
    }

    @FXML
    public void save() {
        try {
            UserDao.updateSettings(user, hideAge.isSelected(), hidePosts.isSelected(), hideStatus.isSelected(), hideFriends.isSelected());
        }
        catch (SQLException e) {
            System.out.println("Error updating settings.");
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    @FXML
    public void close() {
        Stage stage = (Stage) hideAge.getScene().getWindow();
        stage.close();
    }
}
