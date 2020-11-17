package Course.Overflow.Course;

import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.Student;
import Course.Overflow.Teacher.CreateCourse.Curriculum.Week;
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

    public Course() {

    }
    
    public Course(Integer id) {
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM COURSE WHERE ID = #", id.toString());
        try {
            rs.next();
            title = rs.getString("TITLE");
            subTitle = rs.getString("SUBTITLE");
            mainPrice = rs.getDouble("PRICE");
            off = rs.getDouble("OFFER");
            publishDate = rs.getDate("PUBLISH_DATE");
        
            isApproved = ToolKit.DBoolToJBool(rs.getString("IS_APPROVED"));
            teacher = new Teacher(rs.getString("TEACHER_ID"));
            imageFile = new Files(rs.getInt("COVER_ID"));
            subCategory = new Category(rs.getInt("CATEGORY_ID"));
            mainCategory = subCategory.getParent();

            languages = Language.getLanguages(this);
            properties = Property.getProperties(this);

            description = rs.getString("DESCRIPTION");
//            Rating will be added
//            Review will be added
            if(!rs.getString("OUTCOMES").equals("")) outcomes = rs.getString("OUTCOMES").split("><");
            prerequisitives = rs.getString("PREREQUISITES").split("><");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Course(String title, String subTitle, String description, Double price, Files cover, Category subCategory) {
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
        this.properties = new ArrayList<Property>();

        DB.execute(
                "INSERT INTO COURSE(ID, TITLE, SUBTITLE, DESCRIPTION, PRICE, PUBLISH_DATE, IS_APPROVED, TEACHER_ID, COVER_ID, CATEGORY_ID)"
                + "VALUES(#, '#', '#', '#', #, #, '#', '#', #, #)",
                id.toString(), title, subTitle, description, price.toString(), ToolKit.JDateToDDate(publishDate), ToolKit.JBoolToDBool(isApproved),
                teacher.getUsername().toString(), imageFile.getId().toString(), subCategory.getId().toString()
        );
    }

    public Integer getId() {
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
        return CourseRating.getValue(this);
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
        DB.execute("UPDATE COURSE SET CATEGORY_ID = # WHERE ID = #", subCategory.getId().toString(), id.toString());
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
        DB.execute("UPDATE COURSE SET PRICE = # WHERE ID = #", mainPrice.toString(), id.toString());
    }

    public Double getOff() {
        return off;
    }

    public void setOff(Double off) {
        this.off = off;
        DB.execute("UPDATE COURSE SET OFFER = # WHERE ID = #", off.toString(), id.toString());
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
        DB.execute("UPDATE COURSE SET LAST_UPDATE = # WHERE ID = #", ToolKit.JDateToDDate(lastUpdate), id.toString());
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
        deleteCourseLanguage();
        for (Language lang : languages) {
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
        DB.execute("UPDATE COURSE SET DESCRIPTION = '#' WHERE ID = #", description, id.toString());
    }

    public Files getCourseImage() {
        return imageFile;
    }

    public void setCourseImage(Files imageFile) {
        this.imageFile = imageFile;
        DB.execute("UPDATE COURSE SET COVER_ID = # WHERE ID = #", imageFile.getId().toString(), id.toString());
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String[] getPrerequisitives() {
        return prerequisitives;
    }

    public void setPrerequisitives(String[] prerequisitives) {
        this.prerequisitives = prerequisitives;
    }

    public ArrayList<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<Week> weeks) {
        this.weeks = weeks;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void deleteCourseLanguage() {
        DB.execute("DELETE FROM COURSE_LANGUAGE WHERE COURSE_ID = #", id.toString());
    }

    public void delete() {
        imageFile.delete();
        deleteCourseLanguage();
        for (Property property : properties) {
            property.delete();
        }
//        ArrayList<Review> reveiws;

        for (Week week : weeks) {
            week.delete();
        }
    }

    public static ArrayList<Course> getApprovedCourses() {
        ArrayList<Course> apCourses = new ArrayList<Course>();
        String sql = "SELECT ID FROM COURSE ";
        ResultSet rs = DB.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = Integer.valueOf(rs.getString("ID"));
                Integer ID = new Integer(id);
                apCourses.add(new Course(ID));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("inside course : total course = " + apCourses.size());
        return apCourses;
    }
    
    public void loadAllData(){
        weeks = Week.getWeeks(this);
        reveiws = Review.getList(this);
    }
    
    public Double getCurrentPrice(){
        return getMainPrice() - getMainPrice()*getOff()/100.0;
    }
    
    public Integer getNumOfRating(){
        try {
            ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM RATING WHERE COURSE_ID = #", id.toString());
            rs.next();
            Integer value = rs.getInt("COUNT(*)");
            rs.close();
            return value;
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Integer getRatingOf(Student student) {
        ResultSet rs = DB.executeQuery("SELECT VALUE FROM RATING WHERE COURSE_ID = # AND STUDENT_ID = '#'", id.toString(), student.getUsername());
        try {
            if(rs.next()){
                Integer value = rs.getInt("VALUE");
                rs.close();
                return value;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean isBoughtBy(Student student) {
        try {
            ResultSet rs = DB.executeQuery("SELECT * FROM PURCHASE_HISTORY WHERE COURSE_ID = # AND STUDENT_ID = '#'", id.toString(), student.getUsername());
            if(rs.next()){
                rs.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ArrayList<Course> coursesOf(Teacher aThis) {
        return null;
    }

}
