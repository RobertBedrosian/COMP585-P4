package facebooklite.Controllers;

import facebooklite.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class DashBoardController {
    private User user;

    @FXML
    Label fullName;
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
    @FXML
    TextField ageTextField;
    @FXML
    Button ageSettings;
    boolean  ageIsBeingChanged = false;
    @FXML
    Label ageMessage;

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
        try {
            user = UserDao.getUser(user.getUserName());
        } catch(SQLException e){
            System.out.println(e);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/settings.fxml"));
        loader.setController(new SettingsController(user));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("Add Friend");
            stage.setScene(new Scene(page));
            Scene s = stage.getScene();
            if(!user.getAgeVisibility()) {
                ((CheckBox) (s.lookup("#ageToggle"))).setSelected(true);
            }
            if(!user.getPostVisibility()) {
                ((CheckBox) (s.lookup("#postsToggle"))).setSelected(true);
            }
            if(!user.getStatusVisibility()) {
                ((CheckBox) (s.lookup("#statusToggle"))).setSelected(true);
            }
            if(!user.getFriendsVisibility()) {
                ((CheckBox) (s.lookup("#friendsToggle"))).setSelected(true);
            }

            stage.show();
            updateStatus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void submitPost() {
        System.out.println(newPost.getText());
        if(newPost.getText().length() > 0) {
            try {
                PostsDao.addPost(user, newPost.getText());
                postArea.getChildren().clear();
                initializeFeed();
                newPost.clear();
            } catch (SQLException e) {
                System.out.println("Unable to create post");
            }
        } else {
            createAlertBox("Please enter text you would like to post.");
        }
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
            stage.showAndWait();
            friendArea.getChildren().clear();
            initializeFriends();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeFriends() {
        ;
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
        fullName.setText(user.getFirstName() + " " + user.getLastName());
        age.setText(String.valueOf(user.getAge()) + " Years old");
        updateStatus();
        initializeFriends();
        initializeFeed();

    }

    private void initializeFeed(){
        try {
            ArrayList<Post> postList = PostsDao.getPosts(user);
            if(postList.size() > 0) {
                ArrayList<Pane> posts = new ArrayList();
                postList.forEach((Post post) -> {
                    try {
                        System.out.println(post.getId() + " " + post.getContent());
                        FXMLLoader postLoader = new FXMLLoader(getClass().getResource("/post.fxml"));
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeFriends() {
        try {
            ArrayList<User> friendList = FriendsDao.getFriends(user);
            friendList.forEach((friend) -> System.out.println(friend.getId()));
            if(friendList.size() > 0) {
                ArrayList<Pane> friends = new ArrayList();
                friendList.forEach((friend) -> {
                    try {
                        FXMLLoader friendLoader = new FXMLLoader(getClass().getResource("/friend.fxml"));
                        FriendController friendController = new FriendController(user, friend);
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

    @FXML
    private void changeAge(ActionEvent e){
            ageTextField.setText(Integer.toString(user.getAge()));
            ageTextField.setVisible(true);
            ageMessage.setVisible(true);
    }

    @FXML
    private void onEnter(ActionEvent e) throws SQLException {
        int ageTemp = user.getAge();

        if (ageTextField.getText().length() == 0){
            //do nothing. keep age the same
        }
        else{

            try{
                ageTemp = Integer.parseInt(ageTextField.getText());
            }
            catch (NumberFormatException err){
                createAlertBox("Enter numbers only.");
            }

            if (ageTemp >= 13 && ageTemp <= 120 ){
                UserDao.changeUserAge(user.getUserName(), ageTemp);
                age.setText(Integer.toString(ageTemp));
                user.setAge(ageTemp);
            }
            else{
                createAlertBox("Enter an age between 13 and 120.");
            }
        }
        ageTextField.setVisible(false);
        ageMessage.setVisible(false);
    }

    private void createAlertBox(String message){
        Alert alertBox = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alertBox.showAndWait();
    }


    private void updateStatus() {
        status.setText(user.getStatus());
    }
}
