
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
            if(rs.next()==true)
            {
            type = rs.getString("TYPE");
            adminId = rs.getString("ADMIN_ID");
            }
            else
            {
                System.out.println("no photo found");
            }
            
            
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
            if(rs.next()==true)
            {
                return new FileType(rs.getInt("ID"));
            }
            else
                System.out.println("no photo");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FileType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

