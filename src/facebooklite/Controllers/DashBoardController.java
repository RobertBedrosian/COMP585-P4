package facebooklite.Controllers;

import facebooklite.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class DashBoardController {
    private User user;

    @FXML
    ImageView userImage;
    @FXML
    Label firstName;
    @FXML
    Label lastName;
    @FXML
    Label age;
    @FXML
    Label status;
    @FXML
    TableView friendTable;
    @FXML
    TextField newPost;
    @FXML
    TextArea userFeed;

    public DashBoardController(User user){
        this.user = user;
    }

    @FXML
    public void changeStatus() {

    }

    @FXML
    public void showSettings() {

    }

    @FXML
    public void submitPost() {

    }

    @FXML
    public void initialize() {
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        age.setText(String.valueOf(user.getAge()));
        status.setText(user.getStatus());
        initializeFriends();
        initializeFeed();
    }

    private void initializeFeed() {
    }

    private void initializeFriends() {

    }
}
