/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    public Language(Integer id){
        try {
            this.id = id;
            ResultSet rs = DB.executeQuery("SELECT * FROM LANGUAGE WHERE ID = #", id.toString());
            if(!rs.next()) return;
            this.name = rs.getString("NAME");
            this.adminId = rs.getString("ADMIN_ID");
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public static ArrayList<Language> getList(){
        ArrayList<Language> list = new ArrayList<>();
        ResultSet rs = DB.executeQuery("SELECT * FROM LANGUAGE");
        try {
            while(rs.next()){
                list.add(new Language(rs.getInt("ID"), rs.getString("NAME"), rs.getString("ADMIN_ID")));
            }
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
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
        return languages;
    }
    
}
