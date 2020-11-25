/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.DB;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.Student;
import Course.Overflow.Teacher.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CourseRating {

    Integer id;
    Student student;
    Double value;
    Date time;

    
    public CourseRating(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM RATING WHERE ID = #", id.toString());
        try {
            rs.next();
            student = new Student(rs.getString("STUDENT_ID"));
            value = rs.getDouble("VALUE");
            time = rs.getDate("TIME");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CourseRating.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public CourseRating(Integer courseId, Student student) {
        this.student = student;
        ResultSet rs = DB.executeQuery("SELECT * FROM RATING WHERE COURSE_ID = # AND STUDENT_ID = '#'", courseId.toString(), student.getUsername());
        try {
            if(rs.next()){
                id = rs.getInt("ID");
                value = rs.getDouble("VALUE");
                time = rs.getDate("TIME");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CourseRating.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public CourseRating(Course course, Student student, Double value){
        this.id = DB.generateId("RATING");
        this.student = student;
        this.value = value;
        DB.execute("INSERT INTO RATING(ID, COURSE_ID, STUDENT_ID, VALUE, TIME) VALUES(#, #, '#', #, #)", id.toString(), course.getId().toString(), student.getUsername(), value.toString(), ToolKit.getCurTimeDB());
    }
    
    public static Double getValue(Course course){
        ResultSet rs = DB.executeQuery("SELECT AVG(VALUE) AVG FROM RATING WHERE COURSE_ID = #", course.getId().toString());
        try {
            rs.next();
            Double value = rs.getDouble("AVG");
            rs.close();
            return value;
        } catch (SQLException ex) {
            Logger.getLogger(CourseRating.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }
    
    public static Double getValue(Teacher teacher){
        ResultSet rs = DB.executeQuery("SELECT AVG(VALUE) AVG FROM RATING WHERE COURSE_ID = ANY(SELECT ID FROM COURSE WHERE TEACHER_ID = '#')", teacher.getUsername());
        try {
            rs.next();
            Double value = rs.getDouble("AVG");
            rs.close();
            return value;
        } catch (SQLException ex) {
            Logger.getLogger(CourseRating.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }
    
    public static Integer getCount(Course course, Integer value) {
        ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM RATING WHERE COURSE_ID = # AND VALUE = #", course.getId().toString(), value.toString());
        Integer result = 0;
        try {
            if(rs.next()){
                result = rs.getInt("COUNT(*)");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CourseRating.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static Integer getValue(Course course, Student student) {
        ResultSet rs = DB.executeQuery("SELECT VALUE FROM RATING WHERE COURSE_ID = # AND STUDENT_ID = '#'", course.getId().toString(), student.getUsername());
        Integer result = 0;
        try {
            if(rs.next()){
                result = rs.getInt("VALUE");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CourseRating.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static void delete(Course course) {
        DB.execute("DELETE FROM RATING WHERE COURSE_ID = #", course.getId().toString());
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
