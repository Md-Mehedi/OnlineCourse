package Course.Overflow.Teacher;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.CourseRating;
import Course.Overflow.DB;
import Course.Overflow.Global.Designation;
import Course.Overflow.Global.Person;
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
public class Teacher extends Person {

    private Designation designation;

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
        DB.execute("UPDATE TEACHER SET DESIGNATION_ID = '#'", designation.getId().toString());
    }

    public Teacher(AccountType accountType, String username, String email, String password, String firstName, String lastName, String about, Date dob){
        super(accountType, username, email, password, firstName, lastName, about, dob);
        DB.execute("INSERT INTO TEACHER(ID) VALUES('#')", username);
    }

    public Teacher(String username) {
        super(username);
        String sql = "SELECT DESIGNATION_ID FROM TEACHER WHERE ID = '#'";
        ResultSet rs = DB.executeQuery(sql, username);
        try {
            rs.next();
            if (rs.getInt("DESIGNATION_ID") != 0) {
                designation = new Designation(rs.getInt("DESIGNATION_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean exist(String username) {
        return DB.valueExist("TEACHER", "ID", username);
    }

    public Integer getNumOfReview() {
        Integer value = null;
        ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM REVIEW WHERE COURSE_ID = ANY(SELECT ID FROM COURSE WHERE TEACHER_ID = '#')", getUsername());
        try {
            rs.next();
            value = rs.getInt("COUNT(*)");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    public Integer getNumOfStudent(){
        try {
            ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM PURCHASE_HISTORY WHERE COURSE_ID = ANY(SELECT ID FROM COURSE WHERE TEACHER_ID = '#')", getUsername());
            Integer value;
            rs.next();
            value = rs.getInt("COUNT(*)");
            rs.close();
            return value;
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public Integer getNumOfCourse(){
        ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM COURSE WHERE TEACHER_ID = '#'", getUsername());
        Integer value = null;
        try {
            rs.next();
            value = rs.getInt("COUNT(*)");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    public Double getRating(){
        return CourseRating.getValue(this);
    }

    public ArrayList<Course> getCourses() {
        return Course.coursesOf(this);
    }

    public Integer getNumOfRating() {
        ResultSet rs = DB.executeQuery("SELECT COUNT(*) FROM RATING WHERE COURSE_ID = ANY(SELECT ID FROM COURSE WHERE TEACHER_ID = '#')", getUsername());
        Integer value = null;
        try {
            rs.next();
            value = rs.getInt("COUNT(*)");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    public static ArrayList<Course> getCreatedCourses(String tchname) {
        String sql = "SELECT ID FROM COURSE WHERE TEACHER_ID = '#' ";
        ResultSet rs = DB.executeQuery(sql, tchname);
        ArrayList<Course> courses = new ArrayList<Course>();

        try {
            while (rs.next()) {
                courses.add(new Course(Integer.valueOf(rs.getString("ID"))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    public Double getTotalRevenue() {
        Double value = 0.0;
        try {
            ResultSet rs = DB.executeQuery("SELECT SUM(COST) FROM PURCHASE_HISTORY WHERE COURSE_ID = ANY(SELECT ID FROM COURSE WHERE TEACHER_ID = '#')", getUsername());
            if(rs.next()){
                value = rs.getDouble("SUM(COST)");
            }
            rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    public static ArrayList<Teacher> getTeacherList()
    {
        String sql = "SELECT ID FROM TEACHER ";
        ArrayList<Teacher> list = new ArrayList<Teacher>();
        ResultSet rs = DB.executeQuery(sql);
        try {
            while(rs.next())
            {
                list.add(new Teacher(rs.getString("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
