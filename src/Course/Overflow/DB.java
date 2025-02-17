package Course.Overflow;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**3
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
            if(System.getProperty("user.name").equals("ASUS")){
                System.out.println("USER : MEHEDI");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:globaldb", "COURSE_OVERFLOW", "co");
            }
            else{
                System.out.println("USER : SHAMMYA");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "COURSE_OVERFLOW", "co");
            }
            st = con.createStatement();
//            System.out.println("Connection established");
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
    
    public static ResultSet executeQuery(String sql, String... arg) { // Replace # mark with value
        //System.out.println(sql);
        for (String value : arg) {
            if(!value.contains("TO_DATE")) value = value.replace("'", "''");
            value = value.replace("#", "^^^");
            value = value.replace("$", "&&&&&");
            sql = sql.replaceFirst("#", value);
        }
        sql = sql.replace("^^^", "#");
        sql = sql.replace("&&&&&", "$");
        
        try {
//            System.out.println(sql);System.out.println("");
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
//            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            startConnection();
        }
        return rs;
    }
    
    public static boolean execute(String sql, String ... arg) { // Replace # mark with value
        //System.out.println(sql);
        for (String value : arg) {
            if(!value.contains("TO_DATE")) value = value.replace("'", "''");
            value = value.replace("#", "^^^");
            value = value.replace("$", "&&&&&");
            sql = sql.replaceFirst("#", value);
        }
        sql = sql.replace("^^^", "#");
        sql = sql.replace("&&&&&", "$");
        
        try {
//           System.out.println(sql);System.out.println("");
            return st.execute(sql);
        } catch (SQLException ex) {
//            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            startConnection();
        }
        return false;
    }
    
    public static Integer generateId(String tableName, String idName){
        ResultSet rs = executeQuery("SELECT NVL(MAX(#), 0)+1 # FROM #", idName, idName, tableName);
        try {
            rs.next();
            Integer IN = new Integer(rs.getInt(idName));
            rs.close();
            return IN;
        } catch (SQLException ex) {
//            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static Integer generateId(String tableName){
        return generateId(tableName, "ID");
    }

    
    public static boolean valueExist(String table, String column, String val) {
        try {
            String sql = "SELECT * FROM # WHERE # = '#' ";
            ResultSet rs = executeQuery(sql, table, column, val);
            boolean f = rs.next();
            rs.close();
            return f;
        } catch (SQLException ex) {
//            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean valueExistnew(String table, String column, String val) {
        try {
            String sql = "SELECT * FROM # WHERE # = # ";
            ResultSet rs = executeQuery(sql, table, column, val);
            boolean f = rs.next();
            rs.close();
            return f;
        } catch (SQLException ex) {
//            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
