package facebooklite;

import dbutil.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao  {

    public static void insertUser(User user) {
        String statemet =
                        "INSERT INTO users\n" +
                        "(FirstName, LastName, Age, UserName, Salt, HashedPassword)\n" +
                        " VALUES\n" +
                        "( '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getAge()+"', '"+user.getUserName()+"', '"+user.getSalt()+"', '"+user.getHashedPassword()+"');\n" ;
        try{
            DBUtil.dbExecuteUpdate(statemet);
        } catch (SQLException e) {
            System.out.println("Error in insertUser() method");
        }
    }

    public static void getUser(String userName) {

    }

    public static Boolean userExists(String userName) throws SQLException {
        ResultSet rs = DBUtil.dbExecuteQuery("SELECT*FROM users WHERE userName = '"+userName+"' ");
        if (rs.next()){
            return true;
        }
        else{
            return false;
        }
    }


    public static void updateUser(User user) {
    }


    public static void deleteUser(String userName) {

    }
}
