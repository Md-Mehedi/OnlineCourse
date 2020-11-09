/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.TargetStudentPage;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Property;
import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.CourseLandingPage.DetailsController;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import Course.Overflow.Teacher.CreateCourse.Pricing.PricingController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TargetStudentPageController implements Initializable {

    @FXML
    private VBox courseLearningContainer;
    @FXML
    private VBox requContainer;
    @FXML
    private VBox propertiesContainer;

    AnchorPane tempPane;
    @FXML
    private AnchorPane container;
    private AnchorPane mainContainer;
    @FXML
    private Label addLearning;
    @FXML
    private Label addRequirement;
    @FXML
    private Label addProperties;
    private DetailsController detailsCtrl;
    private CurriculumController curriculumCtrl;
    private PricingController pricingCtrl;
    private ArrayList<PropertiesController> propertiesCtrls;
    private ArrayList<AddAnswerController>  courseOutcomesCtrls;
    private ArrayList<AddAnswerController> requCtrls;

    public ArrayList<PropertiesController> getPropertiesCtrls() {
        return propertiesCtrls;
    }

    public void setPropertiesCtrls(ArrayList<PropertiesController> propertiesCtrls) {
        this.propertiesCtrls = propertiesCtrls;
    }

    public ArrayList<AddAnswerController> getCourseOutcomesCtrls() {
        return courseOutcomesCtrls;
    }

    public void setCourseOutcomesCtrls(ArrayList<AddAnswerController> courseOutcomesCtrls) {
        this.courseOutcomesCtrls = courseOutcomesCtrls;
    }

    public ArrayList<AddAnswerController> getRequCtrls() {
        return requCtrls;
    }

    public void setRequCtrls(ArrayList<AddAnswerController> requCtrls) {
        this.requCtrls = requCtrls;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainContainer = container;
        propertiesCtrls = new ArrayList<>();
        courseOutcomesCtrls = new ArrayList<>();
        requCtrls = new ArrayList<>();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
            tempPane = loader.load();
            loader.<AddAnswerController>getController().setParent(this, courseLearningContainer, mainContainer);
            courseLearningContainer.getChildren().add(tempPane);
            courseOutcomesCtrls.add(loader.getController());
            
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
            tempPane = loader.load();
            loader.<AddAnswerController>getController().setParent(this, requContainer, mainContainer);
            requContainer.getChildren().add(tempPane);
            requCtrls.add(loader.getController());
            
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/Properties.fxml"));
            tempPane = loader.load();
            loader.<PropertiesController>getController().setParent(this, propertiesContainer, mainContainer);
            propertiesContainer.getChildren().add(tempPane);
            propertiesCtrls.add(loader.getController());
        } catch (IOException ex) {
            Logger.getLogger(TargetStudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removePropertiesCtrl(PropertiesController ctrl){
        propertiesCtrls.remove(ctrl);
    }

    public void removeCourseLearningCtrl(AddAnswerController ctrl){
        if(courseOutcomesCtrls.contains(ctrl)) courseOutcomesCtrls.remove(ctrl);
    }

    public void removeRequCtrl(AddAnswerController ctrl){
        if(requCtrls.contains(ctrl)) requCtrls.remove(ctrl);
    }
    
    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException {
        Object src = event.getSource();
        ToolTip.invisibleAll();
        if (src == addLearning) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
            AnchorPane pane = loader.load();
            loader.<AddAnswerController>getController().setParent(this, courseLearningContainer, mainContainer);
            courseLearningContainer.getChildren().add(pane);
            courseOutcomesCtrls.add(loader.getController());
        } else if (src == addRequirement) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
            AnchorPane pane = loader.load();
            loader.<AddAnswerController>getController().setParent(this, requContainer, mainContainer);
            requContainer.getChildren().add(pane);
            requCtrls.add(loader.getController());
        } else if (src == addProperties) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/Properties.fxml"));
            AnchorPane pane = loader.load();
            loader.<PropertiesController>getController().setParent(this, propertiesContainer, mainContainer);
            propertiesContainer.getChildren().add(pane);
            propertiesCtrls.add(loader.getController());
        }
    }

    public AnchorPane getContainer() {
        return container;
    }

    public void setControllers(CurriculumController curriculumCtrl, DetailsController detailsController, PricingController pricingController) {
        this.detailsCtrl = detailsController;
        this.curriculumCtrl = curriculumCtrl;
        this.pricingCtrl = pricingController;
    }
    
    public void uploadProperties(Course course){
        ArrayList<Property> properties = new ArrayList<Property>();
        for(PropertiesController ctrl : propertiesCtrls){
            properties.add(ctrl.uploadToDB(course));
        }
        course.setProperties(properties);
    }

    public String getOutcomes() {
        String outcomes = "";
        for(AddAnswerController outcomeCtrl : courseOutcomesCtrls){
            if(outcomeCtrl.getValue().equals("")) continue;
            outcomes += (outcomes.equals("") ? "" : "><") + outcomeCtrl.getValue();
        }
        return outcomes;
    }

    public String getPrerequisitives() {
        String prerequisitives = "";
        for(AddAnswerController prerequCtrl : requCtrls){
            if(prerequCtrl.getValue().equals("")) continue;
            prerequisitives += (prerequisitives.equals("") ? "" : "><") + prerequCtrl.getValue();
        }
        return prerequisitives;
    }
    
    public void moveProperty(int idx, int i) {
        ArrayList<PropertiesController> parent = propertiesCtrls;
        if (idx + i < 0 || idx + i == parent.size()) {
            return;
        }
        PropertiesController curBox = parent.get(idx);
        PropertiesController toBox = parent.get(idx + i);
        parent.remove(i == -1 ? curBox : toBox);
        parent.add(idx + (i == -1 ? i : 0), i == -1 ? curBox : toBox);
    }

    public boolean isPassedCondition() {
        for(AddAnswerController outcomes : courseOutcomesCtrls){
            if(outcomes.getValue().equals("")){
                ToolKit.showWarning("Any outcomes field can not be empty.");
                return false;
            }
        }
        if(courseOutcomesCtrls.size()==0) {
            ToolKit.showWarning("Please enter at least one outcome to make your course more beautiful");
            return false;
        }
        for(AddAnswerController r : requCtrls){
            if(r.getValue().equals("")){
                ToolKit.showWarning("Any prerequisitive field can not be empty.");
                return false;
            }
        }
        for(PropertiesController p : propertiesCtrls){
            if(p.getValue().equals("")){
                ToolKit.showWarning("Any properties field can not be empty.");
                return false;
            }
        }
        if(propertiesCtrls.size()==0) {
            ToolKit.showWarning("Please enter at least one properties to make your course more beautiful");
            return false;
        }
        return true;         
    }
}
