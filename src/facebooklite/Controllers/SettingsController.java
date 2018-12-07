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
    CheckBox ageToggle;
    @FXML
    CheckBox postsToggle;
    @FXML
    CheckBox statusToggle;
    @FXML
    CheckBox friendsToggle;

    public SettingsController(User user) {
        this.user = user;
    }

    @FXML
    public void save() {
        try {
            UserDao.updateSettings(user, !ageToggle.isSelected(), !postsToggle.isSelected(), !statusToggle.isSelected(), !friendsToggle.isSelected());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    @FXML
    public void close() {
        Stage stage = (Stage) ageToggle.getScene().getWindow();
        stage.close();
    }
}
