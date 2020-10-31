/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Student;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Person;
import java.util.ArrayList;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Student extends Person{
    ArrayList<Course> courses;

    
    public Student(String username, String email, String password, String firstName, String lastName, String about){
        super(username);
        this.courses = courses;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }    
}
