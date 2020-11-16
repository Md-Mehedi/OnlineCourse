/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global;

import Course.Overflow.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class EducationalStatus {
    Integer id;
    String type;
    String adminId;
    
    public EducationalStatus(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM EDUCATIONAL_STATUS WHERE ID = #", id.toString());
        try {
            if(!rs.next()) {rs.close(); return;}
            type = rs.getString("TYPE");
            adminId = rs.getString("ADMIN_ID");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(EducationalStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EducationalStatus(String type){
        this.type = type;
        ResultSet rs = DB.executeQuery("SELECT * FROM EDUCATIONAL_STATUS WHERE TYPE = '#'", type);
        try {
            if(!rs.next()) {rs.close(); return;}
            id = rs.getInt("ID");
            adminId = rs.getString("ADMIN_ID");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(EducationalStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EducationalStatus(Integer id, String type, String adminId){
        this.id = id;
        this.type = type;
        this.adminId = adminId;
    }
    
    public static ArrayList<EducationalStatus> getList(){
        ArrayList<EducationalStatus> list = new ArrayList<>();
        ResultSet rs = DB.executeQuery("SELECT * FROM EDUCATIONAL_STATUS");
        try {
            while(rs.next()){
                list.add(new EducationalStatus(rs.getInt("ID"), rs.getString("TYPE"), rs.getString("ADMIN_ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(EducationalStatus.class.getName()).log(Level.SEVERE, null, ex);
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
