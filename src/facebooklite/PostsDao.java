package facebooklite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PostsDao {
    // Adds post to the given with the given users id
    public static void addPost(User user, String content) throws SQLException {
        DBUtil.dbExecuteUpdate("INSERT INTO posts (user_id, content) VALUES (?, ?);", user.getId(), content);
    }

    // Removes a post from the posts with the given post id
    public static void removePost(int id) throws SQLException {
        DBUtil.dbExecuteUpdate("DELETE FROM posts WHERE id=?;", id);
    }

    // Gets a list of all the posts of the given user
    public static Map getPosts(User user) throws SQLException {
        Map postList = new HashMap();
        if(UserDao.userExists(user.getUserName())) {
            ResultSet rs = DBUtil.dbExecuteQuery("SELECT id,content FROM posts WHERE user_id=? ORDER BY id DESC;", user.getId());
            while(rs.next()) {
                postList.put(rs.getInt("id"), rs.getString("content"));
            }
        }
        return postList;
    }

    // Gets owner of the post's user id
    public static int getUserOfPost(int id) throws SQLException {
        ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM posts WHERE id = '" + id + "'");
        if( rs.next() ){
            return rs.getInt("user_id");
        } else {
            return -1;
        }
    }
}
