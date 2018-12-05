package facebooklite.Controllers;

import facebooklite.PostsDao;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

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
//        PostsDao.deletePost(user, id);
    }
}
