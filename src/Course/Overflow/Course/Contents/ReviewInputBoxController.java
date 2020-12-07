/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Course.Contents;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.CourseRating;
import Course.Overflow.Course.Review;
import Course.Overflow.Course.Show.CourseDetailsController;
import Course.Overflow.Global.Components.Notification.Notification;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.FloatingPane;
import Course.Overflow.Global.ToolKit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ReviewInputBoxController extends FloatingPane implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private Rating rating;
    @FXML
    private TextArea reviewField;
    @FXML
    private Label submtBtn;
    @FXML
    private Label cancelBtn;
    
    private AnchorPane parent;
    private Course course;
    private CourseDetailsController parentCtrl;
    @FXML
    private Label giveRatingLabel;
    private CourseRating courseRating;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.backPane = container;
        addListener();
    } 

    private void addListener() {
        cancelBtn.setOnMouseClicked((event) -> {
            close();
        });
        submtBtn.setOnMouseClicked((event) -> {
            if(!isConditionPass()) return;
            if(courseRating == null){
                courseRating = new CourseRating(course, GLOBAL.STUDENT, this.rating.getRating());
            }
            Review review = new Review(course, GLOBAL.STUDENT, reviewField.getText(), courseRating);
            Notification.setReview(course, GLOBAL.STUDENT.getUsername()); //Review input Notification
            parentCtrl.addReviewBox(review);
            parentCtrl.refreshData();
            parentCtrl.addRating();
            parentCtrl.removeAddReviewBtn();
            close();
            this.rating.setRating(0);
            this.reviewField.setText("");
        });
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public void setRating(CourseRating courseRating){
        this.courseRating = courseRating;
        this.rating.setRating(courseRating.getValue());
        giveRatingLabel.setText("Your submitted rating : ");
        this.rating.setOnMouseClicked((event) -> {
            this.rating.setRating(courseRating.getValue());
        });
    }

    public void setParent(CourseDetailsController parent) {
        this.parentCtrl = parent;
    }

    private boolean isConditionPass() {
        if(rating.getRating() == 0){
            ToolKit.showWarning("Please give a rating.");
            return false;
        }
        if(!(5 < reviewField.getText().length() && reviewField.getText().length() < 4000)){
            ToolKit.showWarning("Please write a review between 50 to 4000 charecter.");
            return false;
        }
        return true;
    }
    
    
}
