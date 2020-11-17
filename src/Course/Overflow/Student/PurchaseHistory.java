/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Student;

import Course.Overflow.Course.Course;
import Course.Overflow.DB;
import Course.Overflow.Global.ToolKit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class PurchaseHistory {
    Integer id;
    Course course;
    Student student;
    Date time;
    Double cost;
    
    public PurchaseHistory(Integer id){
        try {
            ResultSet rs = DB.executeQuery("SELECT * FROM PURCHASE_HISTORY WHERE ID = #", id.toString());
            rs.next();
            this.id = id;
            course = new Course(rs.getInt("COURSE_ID"));
            student = new Student(rs.getString("STUDENT_ID"));
            time = rs.getDate("TIME");
            cost = rs.getDouble("COST");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PurchaseHistory(Course course, Student student, Double cost){
        this.id = DB.generateId("PURCHASE_HISTORY");
        this.course = course;
        this.student = student;
        this.time = ToolKit.getCurTime();
        this.cost = cost;
        DB.execute("INSERT INTO PURCHASE_HISTORY(ID, COURSE_ID, STUDENT_ID, TIME, COST) VALUES(#, #, '#', #, #)", id.toString(), course.getId().toString(), student.getUsername(), ToolKit.JDateToDDate(time), cost.toString());
    }
}
