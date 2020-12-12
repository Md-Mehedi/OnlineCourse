/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class OverviewController implements Initializable {

    @FXML
    private Label revenueField;
    @FXML
    private JFXButton historyBtn;
    @FXML
    private Label enrollmentField;
    @FXML
    private JFXButton enrollStudentBtn;
    @FXML
    private Label reviewsField;
    @FXML
    private JFXButton reviewerBtn;
    @FXML
    private Label ratingField;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton courseBtn;
    @FXML
    private Label courseNum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
        loadData();
    }    

    private void addListener() {
        courseBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.MyCourse);
        });
        reviewerBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Review);
        });
        enrollStudentBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.EnrolledStudents);
        });
        historyBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.PurchaseHistory);
        });
    }

    private void loadData() {
        Teacher teacher = GLOBAL.TEACHER;
        courseNum.setText(teacher.getNumOfCourse().toString());
        revenueField.setText(ToolKit.DoubleToString(teacher.getTotalRevenue()));
        enrollmentField.setText(teacher.getNumOfStudent().toString());
        reviewsField.setText(teacher.getNumOfReview().toString());
        String ratingValue = (teacher.getNumOfRating()!=0 ? "(" + teacher.getNumOfRating() + " students)" : "" ) + teacher.getRating();
        ratingField.setText(ratingValue);
    }
}
