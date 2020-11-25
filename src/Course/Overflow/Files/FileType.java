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
public enum FileType {
    PICTURE("Picture"),
    VIDEO("Video"),
    PDF("PDF"),
    ARTICLE("Article"),
    LINK("Link"),
    FONT_AWESOME_ICON("FontAwesomeIcon"),
    MATERIAL_ICON("MaterialIcon"),
    SVG("SVG"),
    ;
    
    Integer id;
    String typeName;
    String adminId;

    private FileType(String typeName) {
        this.typeName = typeName;
        ResultSet rs = DB.executeQuery("SELECT * FROM FILE_TYPE WHERE TYPE = '#'", typeName);
        try {
            if (rs.next()) {
                id = rs.getInt("ID");
                adminId = rs.getString("ADMIN_ID");
            } else {
                System.err.println("no file data found");
            }
            rs.close();
            System.out.println(typeName + " is ready to use.");
        } catch (SQLException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static FileType valueOf(Integer id) {
        for(FileType v : values())
            if(v.getId() ==  id) return v;
        throw new IllegalArgumentException();
    }

    public Integer getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getAdminId() {
        return adminId;
    }
    
    /**
     *
     * @param s
     */
}
