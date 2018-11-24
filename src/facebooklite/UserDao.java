package facebooklite;

import dbutil.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao  {

    public static void insertUser(User user) {
        String statemet =
                        "INSERT INTO users\n" +
                        "(FirstName, LastName, Age, UserName)\n" +
                        " VALUES\n" +
                        "( '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getAge()+"', '"+user.getUserName()+"');\n" ;

        try{
            DBUtil.dbExecuteUpdate(statemet);
        } catch (SQLException e) {
            System.out.println("Error in insertUser() method");
        }
    }

    public static User getUser(String userName) throws SQLException {
        ResultSet rs = DBUtil.dbExecuteQuery("SELECT*FROM users WHERE UserName =  '"+userName+"' ");
        if (rs.next()){
            User user = getUserFromResultSet(rs);
            return user;
        }
        else{
            return null;
        }
    }


    public static User getUserFromResultSet(ResultSet rs) throws SQLException
    {
        User user = null;
        if (rs != null) {
            user = new User();
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setAge(rs.getInt("Age"));
            user.setUserName(rs.getString("UserName"));
            user.setSalt(rs.getBytes("Salt"));
            user.setHashedPassword(rs.getBytes("HashedPassword"));
        }
        return user;
    }


    public static Boolean userExists(String userName) throws SQLException {
        ResultSet rs = DBUtil.dbExecuteQuery("SELECT*FROM users WHERE UserName = '"+userName+"' ");
        if (rs.next()){
            return true;
        }
        else{
            return false;
        }
    }

    public static void updateUserSaltAndPass(String userName, byte[] salt, byte[] hashCode){
        DBUtil.dbUpdateSaltAndPass(userName, salt, hashCode);
    }

    public static void updateUser(String username) {

    }


    public static void deleteUser(String userName) {

    }
}
