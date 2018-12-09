package facebooklite.Controllers;

import facebooklite.FriendsDao;
import facebooklite.User;
import facebooklite.UserDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    public void searchFriends() {
        search.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        if(search.getText().length() > 0) {
            try {
                ArrayList<User> users = UserDao.getUsers(search.getText());
                if( users != null ){
                    friendList.getChildren().clear();
                    for( int i = 0; i < users.size(); i++ ){
                        // check if user is logged in user or already a friend of the logged in user
                        if(!users.get(i).getUserName().equals(user.getUserName())) {
                            try {
                                FXMLLoader friendLoader = new FXMLLoader(getClass().getResource("/friendSelectorfriend.fxml"));
                                FriendController friendController = new FriendController(user, users.get(i));
                                friendLoader.setController(friendController);
                                Pane friend = friendLoader.load();
                                friendList.getChildren().add(friend);
                                boolean friendship = FriendsDao.existsFriendship(user, users.get(i));
                                if(friendship){
                                    Scene s = friend.getScene();
                                    Button b = (Button)(s.lookup("#userName").getParent().getChildrenUnmodifiable().get(1));
                                    b.setDisable(true);
                                    b.setText("Added");
                                }
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                        }
                    }
                    if( friendList.getChildren().size() == 0 ){
                        friendList.getChildren().clear();
                        friendList.getChildren().add(new Label("No results."));
                    }
                } else {
                    System.out.println("No friends");
                    friendList.getChildren().clear();
                    friendList.getChildren().add(new Label("No results."));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            search.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
    }

    @FXML
    public void done() {
        Stage stage = (Stage) search.getScene().getWindow();
        stage.close();
    }
}
