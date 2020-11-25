/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Layout.FloatingPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CongratulationFlowPaneController extends FloatingPane implements Initializable {

    @FXML
    private ImageView coursePhoto;
    @FXML
    private Label courseName;
    @FXML
    private AnchorPane container;
    private Course course;
    private CheckoutPageController parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.backPane = container;
    }    

    void loadData(Course course) {
        this.course = course;
        coursePhoto.setImage(new Image(course.getCourseImage().getContent()));
        courseName.setText(course.getTitle());
    }

    void setParent(CheckoutPageController parent) {
        this.parent = parent;
    }
    
}
