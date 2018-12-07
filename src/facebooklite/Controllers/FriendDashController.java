package facebooklite.Controllers;

import facebooklite.User;
import facebooklite.UserDao;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
        fullName.setText(user.getFirstName() + " " + user.getLastName());
        age.setText(String.valueOf(user.getAge()));
        status.setText(user.getStatus());
        // add friends
        // add posts

        try {
            User temp = UserDao.getUser(user.getUserName());
            System.out.println(user.getAgeVisibility());
        } catch(SQLException e){
            System.out.println(e);
        }
    }
}
