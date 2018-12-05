package facebooklite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendsDao {
    // Sets user and friend as friends by adding the references in the Friends table
    public static void addFriend(User user, User friend) {
        try {
            DBUtil.dbExecuteUpdate("INSERT INTO friends (user_id1, user_id2) VALUES (? ?)", user.getId(), friend.getId());
            DBUtil.dbExecuteUpdate("INSERT INTO friends (user_id1, user_id2) VALUES (? ?)", friend.getId(), user.getId());
        }
        catch (SQLException e) {
            System.out.println("Error adding friend");
            e.printStackTrace();
        }
    }

    // Sets the user as not friends with friend anymore by removing the references in the Friends table
    public static void removeFriend(User user, User friend) {
        try {
            DBUtil.dbExecuteUpdate("DELETE FROM friends WHERE user_id1=? and user_id2=?", user.getId(), friend.getId());
            DBUtil.dbExecuteUpdate("DELETE FROM friends WHERE user_id1=? and user_id2=?", friend.getId(), user.getId());
        }
        catch (SQLException e) {
            System.out.println("Error removing friend");
            e.printStackTrace();
        }
    }

    // Gets a list of all the friends of the given user
    public static ArrayList<User> getFriends(User user) throws SQLException {
        ArrayList<User> friends = new ArrayList<>();
        if(UserDao.userExists(user.getUserName())) {
            ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM friends, users WHERE friends.user_id1=? and users.id=friends.user_id2;", user.getId());
            while(rs.next()) {
                friends.add(UserDao.getUser(rs.getString("username")));
            }
        }
        return friends;
    }
}
