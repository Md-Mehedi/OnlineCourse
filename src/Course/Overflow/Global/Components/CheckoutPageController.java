/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Show.CourseDetailsController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.FloatingPane;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.Student;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CheckoutPageController extends FloatingPane implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private TextField cardNameField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private DatePicker expireField;
    @FXML
    private Label discountPrice;
    @FXML
    private Label totalPrice;
    @FXML
    private Label originalPrice;
    @FXML
    private Label completeBtn;
    @FXML
    private ImageView coursePhoto;
    @FXML
    private Label courseName;
    @FXML
    private Label originalPriceBottom;
    @FXML
    private Label discountsPriceBottom;
    private FXMLLoader loader;
    private CongratulationFlowPaneController cngCtrl;
    private Course course;
    private CourseDetailsController parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backPane = container;
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/CongratulationFlowPane.fxml"));
            loader.load();
            cngCtrl = loader.<CongratulationFlowPaneController>getController();
            cngCtrl.setParent(this);
        } catch (IOException ex) {
            Logger.getLogger(CheckoutPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addListener();
    }    

    private void addListener() {
        completeBtn.setOnMouseClicked((event) -> {
            if(!isPassedCondition()) return;
            GLOBAL.STUDENT.boughtCourse(course);
            cngCtrl.loadData(course);
            close();
            parent.removeBuyNowBtn();
            parent.setViewer(CurriculumController.ViewerType.OwnerStudent);
            parent.refreshData();
            parent.refreshCurriculum();
            closeTransition.setOnFinished((event1) -> {
                sPane.toBack();
                cngCtrl.show();
            });
        });
    }

    public void loadData(Course course) {
        if(this.course != course){
            this.course = course;
            Student student = GLOBAL.STUDENT;
            if(student.getCard() != null){
                cardNameField.setText(student.getCard().getNameOnCard());
                cardNumberField.setText(student.getCard().getCardNo());
                expireField.setValue(ToolKit.DateToLocalDate(student.getCard().getExpireDate()));
            }
            coursePhoto.setImage(new Image(course.getCourseImage().getContent()));
            courseName.setText(course.getTitle());
            originalPriceBottom.setText(course.getMainPrice().toString());
            discountsPriceBottom.setText(course.getCurrentPrice().toString());
            originalPrice.setText(course.getMainPrice().toString());
            discountPrice.setText(ToolKit.DoubleToString(course.getMainPrice() - course.getCurrentPrice()).toString());
            totalPrice.setText(course.getCurrentPrice().toString());
        }
    }
    
    private boolean isPassedCondition(){
        if(cardNameField.getText().equals("") || cardNumberField.getText().equals("") || expireField.getValue() == null){
            ToolKit.showWarning("Please set Credit Card information properly.");
            return false;
        }
        return true;
    }

    public void setParent(CourseDetailsController parent) {
        this.parent = parent;
    }
    
    public CourseDetailsController getParent(){
        return parent;
    }
}
