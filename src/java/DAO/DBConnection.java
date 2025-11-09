/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author kpham
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DBConnection{
    public static String driverName= "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=DB_PRJ_Assginment;encrypt=false;";
    private static final String userDB = "sa";
    private static final String passDB = "sa";
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(dbURL,userDB,passDB);
            return con;
        } catch (Exception e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    public static void main(String[] args) {
        try (Connection con=getConnection()){
            if (con!=null) {
                System.out.println("Connect to DB_PRJ_Assginment Success");
            }
        } catch (Exception e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
