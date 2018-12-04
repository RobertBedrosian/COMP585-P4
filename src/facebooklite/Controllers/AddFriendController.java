package facebooklite.Controllers;

import facebooklite.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFriendController {
    private User user;

    @FXML
    TextField search;

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
}
