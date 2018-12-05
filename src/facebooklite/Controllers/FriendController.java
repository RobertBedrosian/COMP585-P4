package facebooklite.Controllers;

import facebooklite.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
