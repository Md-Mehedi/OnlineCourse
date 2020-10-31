/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Teacher;

import Course.Overflow.Course.Course;
import Course.Overflow.DB;
import Course.Overflow.Global.Designation;
import Course.Overflow.Global.Person;
import java.util.ArrayList;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Teacher extends Person{
    private Designation designation;
    int numOfStudent;
    int numOfReviews;
    ArrayList<Course> courses;

    public int getNumOfStudent() {
        return numOfStudent;
    }

    public int getNumOfReviews() {
        return numOfReviews;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public void setNumOfStudent(int numOfStudent) {
        this.numOfStudent = numOfStudent;
    }

    public void setNumOfReviews(int numOfReviews) {
        this.numOfReviews = numOfReviews;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Teacher(String username, String email, String password, String firstName, String lastName, String about){
        super(username, email, password, firstName, lastName, about);
        DB.execute("INSERT INTO INSTRUCTOR(ID) VALUES('#')", username);
    }
}
