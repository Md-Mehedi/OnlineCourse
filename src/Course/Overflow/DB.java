package Course.Overflow;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan , Kazi Wasif Amin
 */
public class DB {

    private static Connection con;
    private static ResultSet rs;
    private static Statement st;

    public static void startConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
//        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

            System.out.println("Driver loaded successfully...");

//            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "COURSE_OVERFLOW", "co");
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

    public static void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public static ResultSet executeQuery(String sql, String... arg) { // Replace # mark with value
        System.out.println(sql);
        for (String value : arg) {
            sql = sql.replaceFirst("#", value);
        }
        
        try {
            System.out.println(sql);System.out.println("");
            rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            startConnection();
        }
        return rs;
    }
    
    public static boolean execute(String sql, String ... arg) { // Replace # mark with value
        System.out.println(sql);
        for (String value : arg) {
            sql = sql.replaceFirst("#", value);
        }
        
        try {
            System.out.println(sql);System.out.println("");
            return st.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            startConnection();
        }
        return false;
    }
    
    public static Integer generateId(String tableName, String idName){
        ResultSet rs = executeQuery("SELECT NVL(MAX(#), 0)+1 # FROM #", idName, idName, tableName);
        try {
            rs.next();
            return rs.getInt(idName);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static Integer generateId(String tableName){
        return generateId(tableName, "ID");
    }
}
