/*
 * To change this license header, choose License Headers in Project Property.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Course;

import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Property {

    Integer id;
    Files icon;
    String text;
    Course course;

    public Property(Integer id, Course course) {
        this.id = id;
        this.course = course;
        ResultSet rs = DB.executeQuery("SELECT * FROM COURSE_PROPERTIES WHERE ID = #", id.toString());
        try {
            if (rs.next()) {
                icon = new Files(rs.getInt("ID"));
                text = rs.getString("TEXT");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Property(Files icon, Course course, String text) {
        this.id = DB.generateId("COURSE_PROPERTIES");
        this.icon = icon;
        this.course = course;
        this.text = text;
        DB.execute("INSERT INTO COURSE_PROPERTIES VALUES(#, #, #, '#')", id.toString(), icon.getId().toString(), course.getId().toString(), text);
    }

    public static ArrayList<Property> getProperties(Course course) {
        ArrayList<Property> properties = new ArrayList<Property>();
        ResultSet rs = DB.executeQuery("SELECT ID FROM COURSE_PROPERTIES WHERE COURSE_ID = #", course.getId().toString());
        try {
            while (rs.next()) {
                properties.add(new Property(rs.getInt("ID"), course));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
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

    public Files getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

}
