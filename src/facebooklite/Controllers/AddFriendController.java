package facebooklite.Controllers;

import facebooklite.FriendsDao;
import facebooklite.User;
import facebooklite.UserDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddFriendController {
    private User user;

    @FXML
    TextField search;
    @FXML
    VBox friendList;

    public AddFriendController(User user) {
        this.user = user;
    }

    @FXML
    public void accept() {

    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) search.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void searchFriends() {
        if(search.getText().length() > 0) {
            try {
                ArrayList<User> users = UserDao.getUsers(search.getText());
                if( users != null ){
                    friendList.getChildren().clear();
                    for( int i = 0; i < users.size(); i++ ){
                        // check if user is logged in user or already a friend of the logged in user
                        boolean friendship = FriendsDao.existsFriendship(user, users.get(i));
                        System.out.println(friendship);
                        if(!users.get(i).getUserName().equals(user.getUserName()) && !friendship) {
                            try {
                                FXMLLoader friendLoader = new FXMLLoader(getClass().getResource("/friendSelectorfriend.fxml"));
                                FriendController friendController = new FriendController(users.get(i));
                                friendLoader.setController(friendController);
                                Pane friend = friendLoader.load();
                                friendList.getChildren().add(friend);
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                        }
                    }
                } else {
                    System.out.println("No friends");
                    friendList.getChildren().clear();
                    friendList.getChildren().add(new Label("No friends."));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            // add red border to search text field
        }
    }

    @FXML
    public void cancelSearch() {
        Stage stage = (Stage) search.getScene().getWindow();
        stage.close();
    }
}
