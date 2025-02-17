package Course.Overflow.Global;

import Course.Overflow.Course.Course;
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
public class Language {

    Integer id;
    String name;
    String adminId;

    public Language(Integer id, String name, String adminId) {
        this.id = id;
        this.name = name;
        this.adminId = adminId;
    }

    public Language(Integer id) {
        try {
            this.id = id;
            ResultSet rs = DB.executeQuery("SELECT * FROM LANGUAGE WHERE ID = #", id.toString());
            if (!rs.next()) {rs.close(); return;}
            this.name = rs.getString("NAME");
            this.adminId = rs.getString("ADMIN_ID");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Language> getList() {
        ArrayList<Language> list = new ArrayList<>();
        ResultSet rs = DB.executeQuery("SELECT * FROM LANGUAGE ORDER BY NAME");
        try {
            while (rs.next()) {
                list.add(new Language(rs.getInt("ID"), rs.getString("NAME"), rs.getString("ADMIN_ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    } 
    
    public static ArrayList<Language> getLanguages(Course course){
        ArrayList<Language> languages = new ArrayList<Language>();
        ResultSet rs = DB.executeQuery("SELECT LANGUAGE_ID FROM COURSE_LANGUAGE WHERE COURSE_ID = #", course.getId().toString());
        try {
            while(rs.next()){
                languages.add(new Language(rs.getInt("LANGUAGE_ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
        return languages;
    }

    public static void createNewLanguage(String language) {
        String sql = "INSERT INTO LANGUAGE (ID,NAME,ADMIN_ID) VALUES(#,'#','#')";
        boolean x = DB.execute(sql, DB.generateId("LANGUAGE").toString(), language, GLOBAL.ADMIN.getUsername());
    }

    public static void changeLanguageName(String oldName, String newName) {
        ResultSet rs = DB.executeQuery("SELECT ID FROM LANGUAGE WHERE NAME = '" + oldName + "'");
        try {
            rs.next();
            if(oldName!=newName)
            {
                String sql = "UPDATE LANGUAGE SET NAME = '#' WHERE ID = #";
                boolean x = DB.execute(sql, newName, rs.getString("ID"));
            }
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteLanguage(String selected) {
        ResultSet rs = DB.executeQuery("SELECT ID FROM LANGUAGE WHERE NAME = '#'", selected);
        try {
            rs.next();
            String id = rs.getString("ID");
            DB.execute("DELETE FROM PERSON_LANGUAGE WHERE LANGUAGE_ID = # ", id);
            DB.execute("DELETE FROM LANGUAGE WHERE NAME = '#' ", selected);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
