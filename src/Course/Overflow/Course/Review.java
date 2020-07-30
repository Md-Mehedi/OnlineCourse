/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.Student.Student;
import java.util.Date;

/**
 *
 * @author Md Mehedi Hasan
 */
class Review {
    Student student;
    double rating;
    Date date;
    String text;

    public Student getStudent() {
        return student;
    }

    public double getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public Review(Student student, double rating, Date date, String text) {
        this.student = student;
        this.rating = rating;
        this.date = date;
        this.text = text;
    }
}
