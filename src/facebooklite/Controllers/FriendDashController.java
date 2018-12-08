package facebooklite.Controllers;

import facebooklite.PostsDao;
import facebooklite.User;
import facebooklite.UserDao;
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
    private boolean age_visibility;
    private boolean statuse_visibility;
    private boolean friends_visibility;
    private boolean post_visibility;

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
        fullName.setText(user.getFirstName() + " " + user.getLastName());
        age.setText(String.valueOf(user.getAge()));
        status.setText(user.getStatus());
        // add friends
        // add posts
        setPosts();
    }

    private void setPosts() {
        System.out.println(user.getId());
        try {
            Map<Integer, String> postList = PostsDao.getPosts(user);
            if(postList.size() > 0) {
                ArrayList<Pane> posts = new ArrayList();
                System.out.println(postList.size());
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
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

//    private void setFriends() {
//        ;
//    }
}
