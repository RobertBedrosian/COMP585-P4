package facebooklite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBUtil {
    private Connection conn = null;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost";
    private static final String user = "root";
    private static String pass = null;

    public DBUtil(){

    }

    /**This method sets up a connection with the DB.*/
    public Connection dbConnect() throws SQLException {

        while(true){

            if(pass == null  ){
                pass = getDBPassword();
            }

            try{
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("success!");

                return conn;
            } catch (SQLException e) {
                System.out.println("Wrong password.");
                pass = getDBPassword();
            }
        }

    }

    public void dbDisconnect() throws SQLException {
        if (conn != null && !conn.isClosed()){
            conn.close();
        }
    }

    /**Asks for the admins password to establish a connection.*/
    private String getDBPassword() {
        System.out.print("Admin, enter your DB password:\t");
        Scanner in = new Scanner(System.in);
        System.out.println();
        String pass = in.nextLine();
        return pass;
    }


}
