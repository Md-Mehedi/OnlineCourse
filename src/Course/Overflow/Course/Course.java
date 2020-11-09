/*
 * To change this license header, choose License Headers in Project Property.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.Course.Contents.Week;
import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.Teacher;
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
public class Course {
    Integer id;
    String title;
    String subTitle;
    String description;
    Double mainPrice;
    Double off;
    Date publishDate;
    Date lastUpdate;
    boolean isApproved;
    Teacher teacher;
    Files imageFile;
    Category mainCategory;
    Category subCategory;
    
    ArrayList<Language> languages;
    ArrayList<Property> properties;
    Double rating;
    ArrayList<Review> reveiws;
    
    Integer numOfStudents;
    String[] prerequisitives;
    String[] outcomes;
    
    ArrayList<Week> weeks;
//    CourseContent content;
//    Media promotionalVideo;
//    String level;
//    PromoCode promo;
    
    public Course(){
        
    }
    
    public Course(Integer id){
        this.id = id;
        System.out.println("Course constructor is not set properly...");
    }
    
    public Course(String title, String subTitle, String description, Double price, Files cover, Category subCategory){
        this.id = DB.generateId("COURSE");
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.mainPrice = price;
        this.imageFile = cover;
        this.subCategory = subCategory;
        this.publishDate = ToolKit.getCurTime();
        this.isApproved = false;
        this.teacher = GLOBAL.TEACHER;
        
        
        DB.execute(
              "INSERT INTO COURSE(ID, TITLE, SUBTITLE, DESCRIPTION, PRICE, PUBLISH_DATE, IS_APPROVED, TEACHER_ID, COVER_ID, CATEGORY_ID)"
                    + "VALUES(#, '#', '#', '#', #, #, '#', '#', #, #)", 
              id.toString(), title, subTitle, description, price.toString(), ToolKit.JDateToDDate(publishDate), ToolKit.JBoolToDBool(isApproved), 
              teacher.getUsername().toString(), imageFile.getId().toString(), subCategory.getId().toString()
        );
    }
    
    public Integer getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        DB.execute("UPDATE COURSE SET SUBTITLE = '#' WHERE ID = #", subTitle, id.toString());
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String[] getPrerequisitive() {
        return prerequisitives;
    }

    public void setPrerequisitive(String requisitive) {
        this.prerequisitives = requisitive.split("><", 0);
        DB.execute("UPDATE COURSE SET PREREQUISITES = '#' WHERE ID = #", requisitive, id.toString());
    }

    public ArrayList<Review> getReveiws() {
        return reveiws;
    }

    public void setReveiws(ArrayList<Review> reveiws) {
        this.reveiws = reveiws;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

    public Category getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Category subCategory) {
        this.subCategory = subCategory;
        this.mainCategory = subCategory.getParent();
        DB.execute("UPDATE COURSE SET CATEGORY_ID = # WHERE ID = #", subCategory.getId().toString(),id.toString());
    }

    public void setTitle(String title) {
        this.title = title;
        DB.execute("UPDATE COURSE SET TITLE = '#' WHERE ID = #", title, id.toString());
    }

    public Integer getNumOfStudents() {
        return numOfStudents;
    }

    public void setNumOfStudents(Integer numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    public Double getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(Double mainPrice) {
        this.mainPrice = mainPrice;
        DB.execute("UPDATE COURSE SET PRICE = # WHERE ID = #", mainPrice.toString(),id.toString());
    }

    public Double getOff() {
        return off;
    }

    public void setOff(Double off) {
        this.off = off;
        DB.execute("UPDATE COURSE SET OFFER = # WHERE ID = #", off.toString(),id.toString());
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public Date getCreateDate() {
        return publishDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        DB.execute("UPDATE COURSE SET LAST_UPDATE = # WHERE ID = #", ToolKit.JDateToDDate(lastUpdate),id.toString());
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
        ResultSet rs = DB.executeQuery("SELECT * FROM COURSE_LANGUAGE WHERE COURSE_ID = '#'", id.toString());
        try {
            while(rs.next()){
                DB.execute("DELETE FROM COURSE_LANGUAGE WHERE ID = #", rs.getString("ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(languages.size() == 0){
            return;
        }
        for(Language lang : languages){
            Integer id = DB.generateId("COURSE_LANGUAGE");
            DB.execute("INSERT INTO COURSE_LANGUAGE VALUES(#, '#', #)", id.toString(), this.id.toString(), lang.getId().toString());
        }
    }

    public String[] getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(String outcome) {
        this.outcomes = outcome.split("><", 0);
        DB.execute("UPDATE COURSE SET OUTCOMES = '#' WHERE ID = #", outcome, id.toString());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        DB.execute("UPDATE COURSE SET DESCRIPTION = # WHERE ID = #", description,id.toString());
    }

    public Files getCourseImage() {
        return imageFile;
    }

    public void setCourseImage(Files imageFile) {
        this.imageFile = imageFile;
        DB.execute("UPDATE COURSE SET COVER_ID = # WHERE ID = #", imageFile.getId().toString(),id.toString());
    }
}
