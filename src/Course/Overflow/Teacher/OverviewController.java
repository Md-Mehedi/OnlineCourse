/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class OverviewController implements Initializable {

    @FXML
    private Label revenueField;
    @FXML
    private Label historyBtn;
    @FXML
    private Label enrollmentField;
    @FXML
    private Label enrollStudentBtn;
    @FXML
    private Label reviewsField;
    @FXML
    private Label reviewerBtn;
    @FXML
    private Label ratingField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
    }    

    private void addListener() {
        reviewerBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Review);
        });
        enrollStudentBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.EnrolledStudents);
        });
    }
    
}
