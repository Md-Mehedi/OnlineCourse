/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Student;

import Course.Overflow.Course.Course;
import Course.Overflow.DB;
import Course.Overflow.Global.EducationalStatus;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Person;
import Course.Overflow.Teacher.Teacher;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Student extends Person{

    private EducationalStatus eduStatus;

    public ArrayList<Course> getCourses() {
        try {
            ArrayList<Course> courses = new ArrayList();
            ResultSet rs = DB.executeQuery("SELECT COURSE_ID FROM PURCHASE_HISTORY WHERE STUDENT_ID = '#' ORDER BY TIME DESC", getUsername());
            while(rs.next()){
                courses.add(new Course(rs.getInt("COURSE_ID")));
            }
            rs.close();
            return courses;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    

    public EducationalStatus getEduStatus() {
        return eduStatus;
    }

    public void setEduStatus(EducationalStatus eduStatus) {
        this.eduStatus = eduStatus;
        System.out.println(eduStatus.getType());
        DB.execute("UPDATE STUDENT SET EDU_STATUS_ID = #", eduStatus.getId().toString());
    }
    
    public Student(AccountType accountType, String username, String email, String password, String firstName, String lastName, String about, Date date){
        super(accountType, username, email, password, firstName, lastName, about, date);
        DB.execute("INSERT INTO STUDENT(ID) VALUES('#')", username);
    }
    
    public Student(String username) {
        super(username);
        String sql = "SELECT EDU_STATUS_ID FROM STUDENT WHERE ID = '#'";
        ResultSet rs = DB.executeQuery(sql, username);
        try {
            rs.next();
            if(rs.getInt("EDU_STATUS_ID")!=0) eduStatus = new EducationalStatus(rs.getInt("EDU_STATUS_ID"));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean exist(String username) {
        return DB.valueExist("STUDENT", "ID", username);
    }

    public void boughtCourse(Course course) {
        new PurchaseHistory(course, GLOBAL.STUDENT, course.getCurrentPrice());
    }

    public Integer getNumOfReview() {
        Integer value = null;
        ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM REVIEW WHERE STUDENT_ID = '#'", getUsername());
        try {
            rs.next();
            value = rs.getInt("COUNT(*)");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    public Integer getNumOfCourse() {
        Integer value = null;
        ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM PURCHASE_HISTORY WHERE STUDENT_ID = '#'", getUsername());
        try {
            rs.next();
            value = rs.getInt("COUNT(*)");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
}
