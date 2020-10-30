/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Student;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Person;
import java.util.ArrayList;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Student extends Person{
    ArrayList<Course> courses;

    public Student(int id, String firstName, String lastName, String shortDescription, String description, Language language, ArrayList<Course> courses) {
        super(id, firstName, lastName, shortDescription, description, language);
        this.courses = courses;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }    
}
