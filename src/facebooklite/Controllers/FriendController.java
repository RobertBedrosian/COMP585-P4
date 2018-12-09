package facebooklite.Controllers;

import facebooklite.FriendsDao;
import facebooklite.User;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    public void add() {
        System.out.println("you: " + loggedUser.getUserName() + ", friend: " + user.getUserName());
        FriendsDao.addFriend(loggedUser, user);
        Button b = (Button)userName.getParent().getChildrenUnmodifiable().get(1);
        b.setText("Added");
        b.setDisable(true);
    }

    @FXML
    public void delete() {
        System.out.println("you: " + loggedUser.getUserName() + ", friend: " + user.getUserName());
        FriendsDao.removeFriend(loggedUser, user);
        VBox friendArea = (VBox)(userName.getParent().getParent().getParent());
        friendArea.getChildren().clear();

        try {
            ArrayList<User> friendList = FriendsDao.getFriends(loggedUser);
            friendList.forEach((friend) -> System.out.println(friend.getId()));
            if(friendList.size() > 0) {
                ArrayList<Pane> friends = new ArrayList();
                friendList.forEach((friend) -> {
                    try {
                        FXMLLoader friendLoader = new FXMLLoader(getClass().getResource("/friend.fxml"));
                        FriendController friendController = new FriendController(loggedUser, friend);
                        friendLoader.setController(friendController);
                        Pane friendPane = friendLoader.load();
                        friends.add(friendPane);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                friendArea.getChildren().addAll(friends);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
