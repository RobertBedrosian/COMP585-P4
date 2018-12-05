package facebooklite.Controllers;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import facebooklite.FriendsDao;
import facebooklite.PostsDao;
import facebooklite.User;
import facebooklite.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class DashBoardController {
    private User user;

    @FXML
    Label fullname;
    @FXML
    Label age;
    @FXML
    Label status;
    @FXML
    VBox friendArea;
    @FXML
    TextArea newPost;
    @FXML
    VBox postArea;

    public DashBoardController(User user){
        this.user = user;
    }

    @FXML
    public void changeStatus() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyStatus.fxml"));
        loader.setController(new ModifyStatusController(user));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("Modify Status");
            stage.setScene(new Scene(page));
            stage.showAndWait();
            updateStatus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showSettings() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/settings.fxml"));
        loader.setController(new SettingsController(user));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("Add Friend");
            stage.setScene(new Scene(page));
            stage.show();
            updateStatus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void submitPost() {

    }

    @FXML
    public void removePosts() {

    }

    @FXML
    public void addFriend() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/friendSelector.fxml"));
        loader.setController(new AddFriendController(user));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("Add Friend");
            stage.setScene(new Scene(page));
            stage.show();
            updateStatus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeFriends() {

    }

    @FXML
    public void logout(ActionEvent event) throws IOException{
        Parent regFXMLParent = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene regFXMLScene = new Scene(regFXMLParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(regFXMLScene);
        window.show();
        System.out.println("Logged out");
    }

    @FXML
    public void initialize() {
        fullname.setText(user.getFirstName() + " " + user.getLastName());
        age.setText(String.valueOf(user.getAge()) + " Years old");
        updateStatus();
        initializeFriends();
        initializeFeed();
    }

    private void initializeFeed(){
        try {
            Map<Integer, String> postList = PostsDao.getPosts(user);
            if(postList.size() > 0) {
                ArrayList<Pane> posts = new ArrayList();
                postList.forEach((Integer id, String content) -> {
                    try {
                        FXMLLoader postLoader = new FXMLLoader(getClass().getResource("/post.fxml"));
                        PostController postController = new PostController(id, content);
                        postLoader.setController(postController);
                        Pane post = postLoader.load();
                        posts.add(post);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                postArea.getChildren().addAll(posts);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeFriends() {
        try {
            ArrayList friendList = FriendsDao.getFriends(user);
            friendList.forEach((friend) -> System.out.println(friend));
            if(friendList.size() > 0) {
                ArrayList<Pane> friends = new ArrayList();
                friendList.forEach((User) -> {
                    try {
                        FXMLLoader friendLoader = new FXMLLoader(getClass().getResource("/friend.fxml"));
                        FriendController friendController = new FriendController(user);
                        friendLoader.setController(friendController);
                        Pane friend = friendLoader.load();
                        friends.add(friend);
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

    private void updateStatus() {
        status.setText(user.getStatus());
    }
}
