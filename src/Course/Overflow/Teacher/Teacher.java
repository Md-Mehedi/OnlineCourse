/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Teacher;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Person;
import java.util.ArrayList;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Teacher extends Person{
    int numOfStudent;
    int numOfReviews;
    ArrayList<Course> courses;

    public Teacher(int id, String firstName, String lastName, String shortDescription, String description, Language language, int numOfStudent, int numOfReviews, ArrayList<Course> courses) {
        super(id, firstName, lastName, shortDescription, description, language);
        this.numOfStudent = numOfStudent;
        this.numOfReviews = numOfReviews;
        this.courses = courses;
    }

    public int getNumOfStudent() {
        return numOfStudent;
    }

    public int getNumOfReviews() {
        return numOfReviews;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
    
}
