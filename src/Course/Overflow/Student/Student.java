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
import java.util.ArrayList;

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
        DB.execute("UPDATE STUDENT SET EDU_STATUS_ID = #", eduStatus.getId().toString());
    }
    
    public Student(AccountType accountType, String username, String email, String password, String firstName, String lastName, String about){
        super(accountType, username, email, password, firstName, lastName, about);
        DB.execute("INSERT INTO STUDENT(ID) VALUES('#')", username);
    }
}
