/*
 * To change this license header, choose License Headers in Project Property.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.Customize.Icon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Property {
    Integer id;
    Icon icon;
    String text;
    Course course;
    
    public Property(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM COURSE_PROPERTIES WHERE ID = #", id.toString());
        try {
            if(rs.next()){
                icon = new Icon(new Files(rs.getInt("ID")));
                text = rs.getString("TEXT");
                this.course = new Course(rs.getInt("COURSE_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Property(Icon icon, Course course, String text){
        this.id = DB.generateId("COURSE_PROPERTIES");
        this.icon = icon;
        this.course = course;
        this.text = text;
        DB.execute("INSERT INTO COURSE_PROPERTIES VALUES(#, #, #, '#')", id.toString(), icon.getFile().getId().toString(), course.getId().toString(), text);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Property(Icon icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }
    
}
