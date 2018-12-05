package facebooklite.Controllers;

import facebooklite.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendController {
    @FXML
    Label userName;

    private User user;

    public FriendController(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        userName.setText(user.getUserName());
    }

    @FXML
    public void openProfile() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/friendDash.fxml"));
        loader.setController(new FriendDashController(user));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("View Profile");
            stage.setScene(new Scene(page));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {

    }
}
