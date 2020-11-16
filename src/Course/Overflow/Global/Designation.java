

package Course.Overflow.Global;

import Course.Overflow.DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Designation {
    Integer id;
    String type;
    String adminId;
    
    public Designation(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM DESIGNATION WHERE ID = #", id.toString());
        try {
            if(!rs.next()) {rs.close(); return;}
            type = rs.getString("TYPE");
            System.out.println(type);
            adminId = rs.getString("ADMIN_ID");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Designation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Designation(String type){
        this.type = type;
        ResultSet rs = DB.executeQuery("SELECT * FROM DESIGNATION WHERE TYPE = '#'", type);
        try {
            if(!rs.next()) {rs.close(); return;}
            id = rs.getInt("ID");
            adminId = rs.getString("ADMIN_ID");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Designation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Designation(Integer id, String type, String adminId){
        this.id = id;
        this.type = type;
        this.adminId = adminId;
    }
    
    public static ArrayList<Designation> getList(){
        ArrayList<Designation> list = new ArrayList<>();
        ResultSet rs = DB.executeQuery("SELECT * FROM DESIGNATION");
        try {
            while(rs.next()){
                list.add(new Designation(rs.getInt("ID"), rs.getString("TYPE"), rs.getString("ADMIN_ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Designation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAdminId() {
        return adminId;
    }
}
