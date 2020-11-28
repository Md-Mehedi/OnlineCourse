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
import Course.Overflow.Teacher.Teacher;
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
public class FAQ {
    Integer id;
    String question;
    String answer;
    Date questionTime;
    Date answerTime;
    Student student;
    Teacher teacher;

    public FAQ() {
    }
    
    
    public FAQ(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM FAQ WHERE ID = #", id.toString());
        try {
            if(rs.next()){
                this.question = rs.getString("QUESTION");
                this.answer = rs.getString("ANSWER");
                this.questionTime = rs.getTimestamp("QUESTION_TIME");
                this.answerTime = rs.getTimestamp("ANSWER_TIME");
                this.student = new Student(rs.getString("STUDENT_ID"));
                ResultSet rsT = DB.executeQuery("SELECT TEACHER_ID FROM COURSE WHERE ID = #", rs.getString("COURSE_ID"));
                if(rsT.next()){
                    this.teacher = new Teacher(rsT.getString("TEACHER_ID"));
                }
                rsT.close();
            }
        rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FAQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        DB.execute("UPDATE FAQ SET ANSWER = '#' WHERE ID = #", answer, id.toString());
        updateAnswerTime();
    }

    public Date getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(Date questionTime) {
        this.questionTime = questionTime;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void updateAnswerTime() {
        this.answerTime = ToolKit.getCurTime();
        DB.execute("UPDATE FAQ SET ANSWER_TIME = # WHERE ID = #", ToolKit.JDateToDDate(answerTime), id.toString());
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public static void delete(Course course) {
        DB.execute("DELETE FROM FAQ WHERE COURSE_ID = #", course.getId().toString());
    }
    
    public FAQ(Course course, Student student, String question){
        this.id = DB.generateId("FAQ");
        this.question = question;
        this.questionTime = ToolKit.getCurTime();
        this.student = student;
        this.teacher = course.getTeacher();
        DB.execute("INSERT INTO FAQ(ID, COURSE_ID, STUDENT_ID, QUESTION, QUESTION_TIME) VALUES(#, #, '#', '#', #)", id.toString(), course.getId().toString(), student.getUsername(), question, ToolKit.JDateToDDate(questionTime));
    }
    
    public static ArrayList<FAQ> getList(Course course){
        ArrayList<FAQ> list = new ArrayList<FAQ>();
        ResultSet rs = DB.executeQuery("SELECT ID FROM FAQ WHERE COURSE_ID = # ORDER BY QUESTION_TIME ASC", course.getId().toString());
        try {
            while(rs.next()){
                list.add(new FAQ(rs.getInt("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;            
    }
    
    public static ArrayList<Pair<Course,ArrayList<FAQ>>> getFAQForTeacherView(){
        ArrayList<Pair<Course,ArrayList<FAQ>>> lists = new ArrayList();
        try {
            ResultSet rsCourse = DB.executeQuery("SELECT COURSE_ID FROM FAQ WHERE COURSE_ID = ANY (SELECT ID FROM COURSE WHERE TEACHER_ID = '#') GROUP BY COURSE_ID ORDER BY MAX(QUESTION_TIME) DESC", GLOBAL.TEACHER.getUsername());
            while(rsCourse.next()){
                ArrayList list = new ArrayList();
                ResultSet rsFAQ = DB.executeQuery("SELECT * FROM FAQ WHERE COURSE_ID = # ORDER BY QUESTION_TIME DESC", rsCourse.getString("COURSE_ID"));
                while(rsFAQ.next()){
                    list.add(new FAQ(rsFAQ.getInt("ID")));
                }
                rsFAQ.close();
                Pair<Course, ArrayList<FAQ>> pair = new Pair(new Course(rsCourse.getInt("COURSE_ID")), list);
                lists.add(pair);
            }
            rsCourse.close();
        } catch (SQLException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }
}
