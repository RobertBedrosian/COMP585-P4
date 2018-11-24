package dbutil;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;
import java.util.Scanner;

public class DBUtil {
    private static Connection conn;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost/Comp585Project4";
    private static final String user = "root";
    private static String pass = null;


    public DBUtil(){

    }


    /**This method sets up a connection with the DB.*/
    private static Connection dbConnect() throws SQLException {

        while(true){

            if(pass == null  ){
                pass = getDBPassword();
            }

            try{
                conn = DriverManager.getConnection(url, user, pass);

                return conn;
            } catch (SQLException e) {
                System.out.println("Wrong password.");
                pass = getDBPassword();
            }
        }

    }

    private static void dbDisconnect() throws SQLException {
        if (conn != null && !conn.isClosed()){
            conn.close();
        }
    }

    /**This method is used when fetching data from the DB.*/
    public static ResultSet dbExecuteQuery(String queryStatement) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try{
            conn = dbConnect();

            /**Gives us a blanck statement object*/
            statement = conn.createStatement();

            resultSet = statement.executeQuery(queryStatement);

            /**Stores data in memory so that we can work on the data without keeping the connection alive
             * with the DB*/
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);

        }
        catch (SQLException e) {
            System.out.println(e);
        }
        finally {
            if( resultSet != null){
                resultSet.close();
            }
            if (statement != null){
                statement.close();
            }

            dbDisconnect();
        }
        return crs;
    }



    /**This method is used to Update, Add, or Remove data in the DB
     *
     * */
    public static void dbExecuteUpdate(String sqlStatement) throws SQLException {
        Statement statement = null;

        try{
            conn = dbConnect();

            statement = conn.createStatement();
            statement.executeUpdate(sqlStatement);

        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            if (statement != null){
                statement.close();
            }

            dbDisconnect();
        }
    }

    public static void dbUpdateSaltAndPass(String userName, byte[] salt, byte[] hashCode){
        String statement = null;
        try{
            conn = dbConnect();
            statement = "UPDATE users SET Salt= ?, HashedPassword=? WHERE username = '"+userName+"' ";

            PreparedStatement prepStmt = conn.prepareStatement(statement);
            prepStmt.setBytes(1, salt);
            prepStmt.setBytes(2,hashCode);

            prepStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**Asks for the admins password to establish a connection.*/
    private static String getDBPassword() {
        System.out.print("Admin, enter your DB password:\t");
        Scanner in = new Scanner(System.in);
        System.out.println();
        String pass = in.nextLine();
        return pass;
    }

}
