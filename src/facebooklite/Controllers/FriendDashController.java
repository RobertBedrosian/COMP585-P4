package facebooklite.Controllers;

import facebooklite.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class FriendDashController {
    private User user;

    @FXML
    Label fullName;
    @FXML
    Label age;
    @FXML
    Label status;
//    @FXML
    //TableView friendsTable;
//    @FXML
    //TableView postTable;


    public FriendDashController(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        fullName.setText(user.getFirstName() + " " + user.getLastName());
        age.setText(String.valueOf(user.getAge()));
        status.setText(user.getStatus());
        // add friends
        // add posts
        applyVisibleSettings();
    }

    private void applyVisibleSettings() {

    }
}
