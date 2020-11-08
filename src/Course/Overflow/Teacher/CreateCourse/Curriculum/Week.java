/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Teacher.CreateCourse.Curriculum;

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
public class Week {
    Integer id;
    Integer weekNo;
    String title;
    Date lastUpdate;
    Course course;
    
    public Week(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM WEEK WHERE ID = #", id.toString());
        try {
            while(rs.next()){
                weekNo = rs.getInt("WEEK_NO");
                title = rs.getString("TITLE");
                lastUpdate = rs.getDate("LAST_UPDATE");
                course = new Course(rs.getInt("COURSE_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Week.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Week(Integer weekNo, String title, Course course){
        this.id = DB.generateId("WEEK");
        this.weekNo = weekNo;
        this.title = title;
        this.course = course;
        this.lastUpdate = ToolKit.getCurTime();
        DB.execute(
              "INSERT INTO WEEK(ID, WEEK_NO, TITLE, LAST_UPDATE, COURSE_ID) VALUES(#, #, '#', #, #)", 
              id.toString(), weekNo.toString(), title, ToolKit.JDateToDDate(lastUpdate), course.getId().toString()
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(Integer weekNo) {
        this.weekNo = weekNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
