package facebooklite;

import java.sql.SQLException;
import java.util.ArrayList;

public class FriendsDao {
    // Sets user and friend as friends by adding the references in the Friends table
    public static void addFriend(User user, User friend) {

    }

    // Sets the user as not friends with friend anymore by removing the references in the Friends table
    public static void removeFriend(User user, User friend) {

    }

    // Gets a list of all the friends of the given user
    public static ArrayList<User> getFriends(User user) throws SQLException {
        if(UserDao.userExists(user.getUserName())) {
            DBUtil.dbExecuteQuery("SELECT * FROM friends, users WHERE friends.user_id1=? and users.id=friends.user_id2;", user.getId());
            return new ArrayList<>();
        }
        else {
            return new ArrayList<>();
        }
    }
}
