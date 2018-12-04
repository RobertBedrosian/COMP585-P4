package facebooklite.Controllers;

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashBoardController {
    private User user;

    @FXML
    Label fullname;
    @FXML
    Label age;
    @FXML
    Label status;
    @FXML
    TableView friendTable;
    @FXML
    TextArea newPost;
    @FXML
    TableView userFeed;

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
    public void removeFriend() {

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

    private void initializeFeed() {
        try {
            ArrayList<User> friendList = PostsDao.getPosts(user);
            if(friendList.size() > 0) {
                for(User u : friendList) {
                    System.out.println(u.getFirstName());
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeFriends() {
        try {
            ArrayList<User> friendList = FriendsDao.getFriends(user);
            if(friendList.size() > 0) {
                for(User u : friendList) {
                    System.out.println(u.getFirstName());
                }
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
