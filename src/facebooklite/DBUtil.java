package facebooklite;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;
import java.util.Scanner;

public class DBUtil {
    private static Connection conn;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/facebook_lite";
    private static final String user = "user";
    private static final String password = "pass";


    public DBUtil(){ }

    /**This method sets up a connection with the DB.*/
    private static Connection dbConnect() throws SQLException {
        try{
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            System.out.println("Error in SQL connection.");
            e.printStackTrace();
        }
        return null;
    }

    private static void dbDisconnect() throws SQLException {
        if (conn != null && !conn.isClosed()){
            conn.close();
        }
    }

    /**This method is used when fetching data from the DB.*/
    public static ResultSet dbExecuteQuery(String queryStatement, Object... args) throws SQLException {
        CachedRowSetImpl crs = null;
        try{
            conn = dbConnect();

            PreparedStatement prepStmt = conn.prepareStatement(queryStatement);
            for(int i = 0; i < args.length; i++) {
                prepStmt.setObject(i+1, args[i]);
            }

            ResultSet resultSet = prepStmt.executeQuery();
            /**Stores data in memory so that we can work on the data without keeping the connection alive
             * with the DB*/
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dbDisconnect();
        }
        return crs;
    }

    /**This method is used to Update, Add, or Remove data in the DB* */
    public static void dbExecuteUpdate(String sqlStatement, Object... args) throws SQLException {
        try{
            conn = dbConnect();

            PreparedStatement prepStmt = conn.prepareStatement(sqlStatement);
            for(int i = 0; i < args.length; i++) {
                prepStmt.setObject(i+1, args[i]);
            }

            prepStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbDisconnect();
        }
    }

    public static void dbUpdateSaltAndPass(String userName, byte[] salt, byte[] hashCode){
        String statement = null;
        try{
            conn = dbConnect();
            statement = "UPDATE users SET salt=?, password=? WHERE username = '"+userName+"' ";

            PreparedStatement prepStmt = conn.prepareStatement(statement);
            prepStmt.setBytes(1, salt);
            prepStmt.setBytes(2,hashCode);

            prepStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
