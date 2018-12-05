package facebooklite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostsDao {
    // Sets use and friend as friends by adding the references in the Friends table
    public static void addPost(User user, String content) {

    }

    // Sets the user as not friends with friend anymore by removing the references in the Friends table
    public static void removePost(User user, int id) {

    }

    // Gets a list of all the friends of the given user
    public static ArrayList<String> getPosts(User user) throws SQLException {
        ArrayList postList = new ArrayList();
        if(UserDao.userExists(user.getUserName())) {
            ResultSet rs = DBUtil.dbExecuteQuery("SELECT content FROM posts WHERE user_id=?;", user.getId());
            while(rs.next()) {
                postList.add(rs.getString("content"));
            }
        }
        return postList;
    }
}
