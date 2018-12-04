package facebooklite;

import java.sql.SQLException;
import java.util.ArrayList;

public class PostsDao {
    // Sets use and friend as friends by adding the references in the Friends table
    public static void addPost(User user, User friend) {

    }

    // Sets the user as not friends with friend anymore by removing the references in the Friends table
    public static void removePost(User user, User friend) {

    }

    // Gets a list of all the friends of the given user
    public static ArrayList<User> getPosts(User user) throws SQLException {
        if(UserDao.userExists(user.getUserName())) {
            DBUtil.dbExecuteQuery("SELECT * FROM posts WHERE user_id=?;", user.getId());
            return new ArrayList<>();
        }
        else {
            return new ArrayList<>();
        }
    }
}
