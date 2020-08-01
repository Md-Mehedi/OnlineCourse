/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Course.Home;

import Course.Overflow.Course.Contents.ReviewInputBoxController;
import Course.Overflow.Global.Components.CheckoutPageController;
import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CourseDetailsController implements Initializable {

    @FXML
    private VBox courseContentBox;
    @FXML
    private VBox requirementBox;
    @FXML
    private Label courseDescription;
    @FXML
    private Label instName;
    @FXML
    private Label instShortDes;
    @FXML
    private Label instRating;
    @FXML
    private Label instReviews;
    @FXML
    private Label instStudents;
    @FXML
    private Label instCourses;
    @FXML
    private Label instDes;
    @FXML
    private VBox reviewsBox;
    @FXML
    private VBox courseLearnBox;
    @FXML
    private ImageView instPhoto;
    @FXML
    private Label title;
    @FXML
    private Label subTitle;
    @FXML
    private Label rating;
    @FXML
    private Label numOfStudent;
    @FXML
    private Label topInstName;
    @FXML
    private Label lastUpdate;
    @FXML
    private Label language;
    @FXML
    private Label price;
    @FXML
    private Label mainPrice;
    @FXML
    private Label off;
    @FXML
    private VBox properties;

    private AnchorPane courseContent;
    @FXML
    private Label writeReviewBtn;
    private FXMLLoader loader;
    private AnchorPane reviewInputPane;
    private ReviewInputBoxController reviewInputCtrl;
    private static boolean isReviewInputBoxAdded = false;
    @FXML
    private Label buyNowButton;
    private AnchorPane checkoutPane;
    private CheckoutPageController checkoutCtrl;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeCourseWeek();
        makeReviewBox();
        
        addListener();
    }    

    public void makeCourseWeek(){
        try {
            courseContent = FXMLLoader.load(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION+"/Curriculum.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        courseContentBox.getChildren().add(courseContent);
    }

    private void makeReviewBox() {
        for(int i=0;i<5;i++){
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_HOME_LOCATION + "/Review.fxml"));
            try {
                //AnchorPane pane = new AnchorPane();
                AnchorPane pane = loader.load();
                reviewsBox.getChildren().add(pane);
            } catch (IOException ex) {
                Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            // Review input box added
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CONTENTS_LOCATION + "/ReviewInputBox.fxml"));
            reviewInputPane = loader.load();
            reviewInputCtrl = loader.<ReviewInputBoxController>getController();
            // Checkout page added
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/CheckoutPage.fxml"));
            checkoutPane = loader.load();
            checkoutCtrl = loader.<CheckoutPageController>getController();
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addListener() {
        instName.setOnMouseClicked(event->{
            GLOBAL.PAGE_CTRL.setTeacherDetailsPage();
        });
        instPhoto.setOnMouseClicked(event->{
            GLOBAL.PAGE_CTRL.setTeacherDetailsPage();
        });
        topInstName.setOnMouseClicked(event->{
            GLOBAL.PAGE_CTRL.setTeacherDetailsPage();
        });
        writeReviewBtn.setOnMouseClicked((event) -> {
            reviewInputCtrl.show();
        });
        buyNowButton.setOnMouseClicked((event) -> {
            checkoutCtrl.show();
        });
        
    }

}
