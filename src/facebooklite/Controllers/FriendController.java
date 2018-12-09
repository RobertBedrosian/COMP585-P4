package facebooklite.Controllers;

import facebooklite.FriendsDao;
import facebooklite.User;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendController {
    @FXML
    Label userName;

    private User user;
    private User loggedUser;

    public FriendController(User user) {
        this.user = user;
    }

    public FriendController(User loggedUser, User user) {
        this.loggedUser = loggedUser;
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
        System.out.println("you: " + loggedUser.getUserName() + ", friend: " + user.getUserName());
        FriendsDao.addFriend(loggedUser, user);
        Button b = (Button)userName.getParent().getChildrenUnmodifiable().get(1);
        b.setText("Added");
        b.setDisable(true);
    }
}
