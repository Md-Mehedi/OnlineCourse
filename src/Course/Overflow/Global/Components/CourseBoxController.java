/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Show.CourseDetailsController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.CoursePage;
import Course.Overflow.Global.Page.PageName;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CourseBoxController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private ImageView picBox;
    @FXML
    private Label teacher;
    @FXML
    private Rating rating;
    @FXML
    private Label ratingValue;
    @FXML
    private Label numOfRating;
    @FXML
    private Label mainPrice;
    @FXML
    private Label offerPrice;
    @FXML
    private AnchorPane container;
    private Course course;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rating.setUpdateOnHover(false);
        rating.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rating.setRating(Double.parseDouble(ratingValue.getText()));
            }
        });
        container.setOnMouseClicked((event)->{
            GLOBAL.PAGE_CTRL.loadPage(PageName.Course);
            CoursePage page = (CoursePage) GLOBAL.PAGE_CTRL.getPage();
            CourseDetailsController ctrl = page.getController();
            ctrl.setCourse(course);
        });
    }    
    
    public void setCourseTitle(String title){
        this.title.setText(title);
    }
    
    private void loadData(){
        title.setText(course.getTitle());
        mainPrice.setText(course.getMainPrice().toString());
        Double offPrice = (course.getMainPrice() - course.getMainPrice()*course.getOff()/100);
        offerPrice.setText(offPrice.toString());
        Image img = new Image(course.getCourseImage().getContent());
        picBox.setImage(img);
        teacher.setText(course.getTeacher().getFirstName()+" "+course.getTeacher().getLastName());
    }

    void setCourse(Course course) {
        this.course = course;
        loadData();
    }
}
