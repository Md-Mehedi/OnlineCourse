/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Review {

    Integer id;
    Student student;
    Date date;
    String text;
    CourseRating rating;
    
    private Review(){}

    public Review(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM REVIEW WHERE ID = #", id.toString());
        try {
            rs.next();
            student = new Student(rs.getString("STUDENT_ID"));
            date = rs.getTimestamp("TIME");
            text = rs.getString("TEXT");
            rating = new CourseRating(rs.getInt("COURSE_ID"), student);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Review(Course course, Student student, String text, Double ratingValue){
        this.id = DB.generateId("REVIEW");
        this.student = student;
        this.text = text;
        this.date = ToolKit.getCurTime();
        this.rating = new CourseRating(course, student, ratingValue);
        DB.execute("INSERT INTO REVIEW(ID, COURSE_ID, STUDENT_ID, TIME, TEXT) VALUES(#, #, '#', #, '#')", id.toString(), course.getId().toString(), student.getUsername(), ToolKit.JDateToDDate(date), text);
    }

    public static ArrayList<Review> getList(Course course){
        ArrayList<Review> list = new ArrayList<Review>();
        ResultSet rs = DB.executeQuery("SELECT ID FROM REVIEW WHERE COURSE_ID = # ORDER BY TIME ASC", course.getId().toString());
        try {
            while(rs.next()){
                list.add(new Review(rs.getInt("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;            
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CourseRating getRating() {
        return rating;
    }

    public void setRating(CourseRating rating) {
        this.rating = rating;
    }
    
    public Integer getCourseId(){
        try {
            ResultSet rs = DB.executeQuery("SELECT COURSE_ID FROM REVIEW WHERE ID = #", id.toString());
            if(rs.next()){
                Integer value = rs.getInt("COURSE_ID");
                rs.close();
                return value;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean isReviewed(Course course, Student student) {
        try {
            ResultSet rs = DB.executeQuery("SELECT * FROM REVIEW WHERE COURSE_ID = # AND STUDENT_ID = '#'", course.getId().toString(), student.getUsername());
            if(rs.next()){
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static ArrayList<ArrayList<Review>> getReviewsForTeacherView(){
        ArrayList<ArrayList<Review>> lists = new ArrayList();
        try {
            ResultSet rsCourse = DB.executeQuery("SELECT COURSE_ID FROM REVIEW WHERE COURSE_ID = ANY (SELECT ID FROM COURSE WHERE TEACHER_ID = '#') GROUP BY COURSE_ID ORDER BY MAX(TIME) DESC", GLOBAL.TEACHER.getUsername());
            while(rsCourse.next()){
                ArrayList list = new ArrayList();
                ResultSet rsReview = DB.executeQuery("SELECT * FROM REVIEW WHERE COURSE_ID = # ORDER BY TIME DESC", rsCourse.getString("COURSE_ID"));
                while(rsReview.next()){
                    Review r = new Review();
                    r.setId(rsReview.getInt("ID"));
                    r.setStudent(new Student(rsReview.getString("STUDENT_ID")));
                    r.setDate(rsReview.getTimestamp("TIME"));
                    r.setText(rsReview.getString("TEXT"));
                    r.setRating(new CourseRating(rsReview.getInt("COURSE_ID"), r.getStudent()));
                    list.add(r);
                }
                rsReview.close();
                lists.add(list);
            }
            rsCourse.close();
        } catch (SQLException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }
}
