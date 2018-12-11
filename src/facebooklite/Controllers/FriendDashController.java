package facebooklite.Controllers;

import facebooklite.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FriendDashController {
    private User user;

    @FXML
    Label fullName;
    @FXML
    Label age;
    @FXML
    Label status;
    @FXML
    VBox postArea;
    @FXML
    VBox friendArea;


    public FriendDashController(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        try {
            user = UserDao.getUser(user.getUserName());
        } catch(SQLException e) {
            System.out.println(e);
        }
        // full name
        fullName.setText(user.getFirstName() + " " + user.getLastName());
        // age
        if(user.getAgeVisibility()) {
            age.setText(String.valueOf(user.getAge()) + " years old");
        }
        // status
        if(user.getStatusVisibility()) {
            status.setText(user.getStatus());
        } else {
            status.setText("");
        }
        // add friends
        if(user.getFriendsVisibility()) {
            setFriends();
        }
        // add posts
        if(user.getPostVisibility()) {
            setPosts();
        }
    }

    private void setPosts() {
        try {
            ArrayList<Post> postList = PostsDao.getPosts(user);
            if(postList.size() > 0) {
                ArrayList<Pane> posts = new ArrayList();
                postList.forEach((Post post) -> {
                    try {
                        FXMLLoader postLoader = new FXMLLoader(getClass().getResource("/friendDashpost.fxml"));
                        PostController postController = new PostController(post.getId(), post.getContent());
                        postLoader.setController(postController);
                        Pane postPane = postLoader.load();
                        posts.add(postPane);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                postArea.getChildren().addAll(posts);
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    private void setFriends() {
        try {
            ArrayList<User> friendList = FriendsDao.getFriends(user);
            System.out.println(friendList.size());
            friendList.forEach((friend) -> System.out.println(friend.getId()));
            if(friendList.size() > 0) {
                ArrayList<Pane> friends = new ArrayList();
                friendList.forEach((friend) -> {
                    try {
                        FXMLLoader friendLoader = new FXMLLoader(getClass().getResource("/friendDashfriend.fxml"));
                        FriendController friendController = new FriendController(friend);
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
