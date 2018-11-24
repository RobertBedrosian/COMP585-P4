package facebooklite;

import dbutil.DBUtil;

import java.sql.SQLException;

public class MainControllerDao {

    public static void createDB(String nameOfDB) throws SQLException {
        DBUtil.dbExecuteUpdate("CREATE DATABASE IF NOT EXISTS " + nameOfDB );
    }

    public static void createTable(String sqlStatment) throws SQLException {
        DBUtil.dbExecuteUpdate(sqlStatment);
    }

}
