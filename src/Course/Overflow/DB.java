/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//@Mehedi Hasan vy apni boss
// shudu boss na onek boro boss
// Ami boss na vya
//second update
// Update Shammya
package Course.Overflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class DB {
    private static Connection con;
    private static ResultSet rs;
    private static Statement st;
    
    public static void startConnection(){
        try {
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
//        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

            System.out.println("Driver loaded successfully...");

            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:globaldb", "COURSE_OVERFLOW", "co");
            st = con.createStatement();

            System.out.println("Connection established");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeConnection(){
        try {
            if(rs!=null) rs.close();
            if(st!=null) st.close();
            if(con!=null) con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ResultSet execute(String sql){
        try {
            System.out.println(sql);
            rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
//    public static boolean execute(String tableName, String fieldName, String value){
//        try {
//            String sql = "SELECT " + fieldName + " From " + tableName + " where " + fieldName + " = '" + value + "'";
//            System.out.println(sql);
//            execute(sql);
//            if(!rs.next()) return false;
//            System.out.println(rs.getString(fieldName));
//            System.out.println(value);
//            System.out.println(rs.getString(fieldName).equals(value));
//            return rs.getString(fieldName).equals(value);
//        } catch (SQLException ex) {
//            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
    
    public static void execute(String sql, String ... arg){ // Replace # mark with value
        for(String value : arg){
            sql = sql.replaceFirst("#", value);
        }
        execute(sql);
    }
    
    // New Function Will Be Here...
    
    // My new line...
}
