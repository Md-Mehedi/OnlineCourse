/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Student;

import Course.Overflow.Course.Course;
import Course.Overflow.DB;
import Course.Overflow.Global.EducationalStatus;
import Course.Overflow.Global.Person;
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
    ArrayList<Course> courses;

    public ArrayList<Course> getCourses() {
        return courses;
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
}
