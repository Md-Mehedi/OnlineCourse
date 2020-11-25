/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Student;

import Course.Overflow.Course.Course;
import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

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
    
    public PurchaseHistory(){}
    
    public PurchaseHistory(Integer id){
        try {
            ResultSet rs = DB.executeQuery("SELECT * FROM PURCHASE_HISTORY WHERE ID = #", id.toString());
            rs.next();
            this.id = id;
            course = new Course(rs.getInt("COURSE_ID"));
            student = new Student(rs.getString("STUDENT_ID"));
            time = rs.getTimestamp("TIME");
            cost = rs.getDouble("COST");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PurchaseHistory(Course course, Student student){
        this.course = course;
        this.student = student;
        try {
            ResultSet rs = DB.executeQuery("SELECT * FROM PURCHASE_HISTORY WHERE COURSE_ID = # AND STUDENT_ID = '#' ORDER BY TIME DESC", course.getId().toString(), student.getUsername());
            if(rs.next()){
                this.id = rs.getInt("ID");
                this.cost = rs.getDouble("COST");
                this.time = rs.getTimestamp("TIME");
            }
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

    public static ArrayList<PurchaseHistory> getPurchasedStudentInfo() {
        ArrayList<PurchaseHistory> list = new ArrayList<PurchaseHistory>();
        try {
            ResultSet rs = DB.executeQuery("SELECT * FROM PURCHASE_HISTORY WHERE COURSE_ID = ANY(SELECT ID FROM COURSE WHERE TEACHER_ID = '#') ORDER BY TIME DESC", GLOBAL.TEACHER.getUsername());
            while(rs.next()){
                PurchaseHistory ph = new PurchaseHistory();
                ph.id = rs.getInt("ID");
                ph.course = new Course(rs.getInt("COURSE_ID"));
                ph.student = new Student(rs.getString("STUDENT_ID"));
                ph.cost = rs.getDouble("COST");
                ph.time = rs.getTimestamp("TIME");
                list.add(ph);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public static ArrayList<Pair<Course, ArrayList<PurchaseHistory>>> getEnrolledStudentList(){
        try {
            ArrayList<Pair<Course, ArrayList<PurchaseHistory>>> lists = new ArrayList();
            ResultSet rsCourse = DB.executeQuery("SELECT COURSE_ID FROM PURCHASE_HISTORY WHERE COURSE_ID = ANY( SELECT ID FROM COURSE WHERE TEACHER_ID = '#' ) GROUP BY COURSE_ID ORDER BY MAX(TIME) DESC", GLOBAL.TEACHER.getUsername());
            while(rsCourse.next()){
                ArrayList<PurchaseHistory> list = new ArrayList();
                Course course = new Course(rsCourse.getInt("COURSE_ID"));
                ResultSet rsPH = DB.executeQuery("SELECT * FROM PURCHASE_HISTORY WHERE COURSE_ID = # ORDER BY TIME DESC", course.getId().toString());
                while(rsPH.next()){
                    PurchaseHistory ph = new PurchaseHistory();
                    ph.setId(rsPH.getInt("ID"));
                    ph.setStudent(new Student(rsPH.getString("STUDENT_ID")));
                    ph.setCourse(course);
                    ph.setTime(rsPH.getTimestamp("TIME"));
                    ph.setCost(rsPH.getDouble("COST"));
                    list.add(ph);
                }
                Pair<Course, ArrayList<PurchaseHistory>> pair = new Pair<>(course, list);
                lists.add(pair);
            }
            return lists;
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void delete(Course course) {
        DB.execute("DELETE FROM PURCHASE_HISTORY WHERE COURSE_ID = #", course.getId().toString());
    }
    
}
