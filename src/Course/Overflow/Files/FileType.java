/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Files;

import Course.Overflow.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class FileType {
    Integer id;
    String type;
    String adminId;

    public FileType(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM FILE_TYPE WHERE ID=#", id.toString());
        try {
            rs.next();
            type = rs.getString("TYPE");
            adminId = rs.getString("ADMIN_ID");
        } catch (SQLException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public static FileType toType(String typeName){
        ResultSet rs = DB.executeQuery("SELECT ID FROM FILE_TYPE WHERE TYPE = '#'", typeName);
        try {
            rs.next();
            return new FileType(rs.getInt("ID"));
        } catch (SQLException ex) {
            Logger.getLogger(FileType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
