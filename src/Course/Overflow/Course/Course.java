/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.Global.Language;
import Course.Overflow.Teacher.Teacher;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Course {
    int id;
    String name;
    String subTitle;
    double rating;
    int numOfStudents;
    Teacher teacher;
    Date createDate;
    Date lastUpdate;
    ArrayList<Language> languages;
    CourseLearning learning;
    CourseContent content;
    Requirement requirement;
    String description;
    ArrayList<Review> reveiws;
    double mainPrice;
    double off;
    Properties properties;
    Image courseImage;
    Media promotionalVideo;
    String level;
    Category mainCategory;
    Category subCategory;
    PromoCode promo;
}
