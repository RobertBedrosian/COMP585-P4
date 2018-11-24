package facebooklite;

import dbutil.DBUtil;

import java.sql.SQLException;


public class UserDao  {

    public static void insertUser(User user) {
        String statemet =
                "BEGIN\n" +
                        "INSERT INTO Users\n" +
                        "(FirstName, LastName, Age, UserName, Salt, HashedPassword)\n" +
                        "VALUES\n" +
                        "( '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getAge()+"', '"+user.getUserName()+"', '"+user.getSalt()+"', '"+user.getHashedPassword()+"');\n" +
                        "END;";
        try{
            DBUtil.dbExecuteUpdate(statemet);
        } catch (SQLException e) {
            System.out.println("Error in insertUser() method");
        }
    }

    public static void getUser(String userName) {

    }


    public static void updateUser(User user) {
    }


    public static void deleteUser(String userName) {

    }
}
