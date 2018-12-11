package facebooklite.Controllers;

import facebooklite.Post;
import facebooklite.PostsDao;
import facebooklite.User;
import facebooklite.UserDao;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PostController {
    @FXML
    TextArea content;

    private int id;
    private String text;

    public PostController(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public void initialize() {
        content.setText(text);
    }

    @FXML
    public void delete() {
        try {
            int user_id = PostsDao.getUserOfPost(id);
            if( user_id != -1 ) {
                User u = UserDao.getUserById(user_id);
                System.out.println(user_id);
            PostsDao.removePost(id);
                VBox postArea = (VBox) (content.getParent().getParent().getParent());
                postArea.getChildren().clear();

            ArrayList<Post> postList = PostsDao.getPosts(u);
            if(postList.size() > 0) {
                ArrayList<Pane> posts = new ArrayList();
                postList.forEach((Post post) -> {
                    try {
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
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
}
