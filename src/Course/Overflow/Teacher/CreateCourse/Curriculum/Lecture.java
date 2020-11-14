/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.ToolKit;
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
public class Lecture {
    Integer id;
    Integer lectureNo;
    String title;
    Date lastUpdate;
    Files file;
    boolean isPreview;
    
    public Lecture(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM LECTURE WHERE ID = #", id.toString());
        try {
            while(rs.next()){
                lectureNo = rs.getInt("LECTURE_NO");
                title = rs.getString("TITLE");
                lastUpdate = rs.getDate("LAST_UPDATE");
                isPreview = ToolKit.DBoolToJBool(rs.getString("IS_PREVIEW"));
                file = new Files(rs.getInt("FILE_ID"));  
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Lecture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Files getFile() {
        return file;
    }

    public void setFile(Files file) {
        this.file = file;
    }
    
    public Lecture(Integer lectureNo, String title, Files file, boolean isPreview, Week week){
        this.id = DB.generateId("LECTURE");
        this.lectureNo = lectureNo;
        this.title = title;
        this.file = file;
        this.isPreview = isPreview;
        this.lastUpdate = ToolKit.getCurTime();
        DB.execute(
              "INSERT INTO LECTURE(ID, LECTURE_NO, TITLE, LAST_UPDATE, IS_PREVIEW, WEEK_ID, FILE_ID) VALUES(#, #, '#', #, '#', #, #)", 
              id.toString(), lectureNo.toString(), title, ToolKit.JDateToDDate(lastUpdate), ToolKit.JBoolToDBool(isPreview), week.getId().toString(), file.getId().toString()
        );
    }
    
    public static ArrayList<Lecture> getLectures(Week week){
        ArrayList<Lecture> lectures = new ArrayList<>();
        ResultSet rs = DB.executeQuery("SELECT ID FROM LECTURE WHERE WEEK_ID = #", week.getId().toString());
        try {
            while(rs.next()){
                lectures.add(new Lecture(rs.getInt("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Lecture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lectures;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLectureNo() {
        return lectureNo;
    }

    public void setLectureNo(Integer lectureNo) {
        this.lectureNo = lectureNo;
        DB.execute("UPDATE LECTURE SET LECTURE_NO = # WHERE ID = #", lectureNo.toString(), id.toString());
        updateTime();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        DB.execute("UPDATE LECTURE SET TITLE = '#' WHERE ID = #", title, id.toString());
        updateTime();
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    private void updateTime() {
        this.lastUpdate = ToolKit.getCurTime();
        DB.execute("UPDATE LECTURE SET LAST_UPDATE = # WHERE ID = #", ToolKit.JDateToDDate(lastUpdate), id.toString());
    }

    public boolean isIsPreview() {
        return isPreview;
    }

    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
        DB.execute("UPDATE LECTURE SET IS_PREVIEW = '#' WHERE ID = #", ToolKit.JBoolToDBool(isPreview), id.toString());
        updateTime();
    }
    
    public void delete(){
        file.delete();
        DB.execute("DELETE LECTURE WHERE ID = #", id.toString());
    }
    
}
