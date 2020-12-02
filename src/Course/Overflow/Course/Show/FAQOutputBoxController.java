/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Course.Show;

import Course.Overflow.Course.FAQ;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FAQOutputBoxController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private VBox container;
    @FXML
    private Circle studentImageCircle;
    @FXML
    private Label studentImageLabel;
    @FXML
    private Label studentName;
    @FXML
    private Label questionDate;
    @FXML
    private Label question;
    @FXML
    private Label noAnswerLabel;
    @FXML
    private VBox answerContainer;
    @FXML
    private Circle teacherImageCircle;
    @FXML
    private Label teacherImageName;
    @FXML
    private Label teacherName;
    @FXML
    private Label answerDate;
    @FXML
    private Label answer;
    private FAQ faq;
    private CurriculumController.ViewerType viewer;
    @FXML
    private JFXButton answerBtn;
    private Object ansInputPane;
    private FAQInputBoxController ansInputCtrl;
    private CourseDetailsController parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void loadData(FAQ faq) {
        this.faq = faq;
        if(faq.getStudent().getPhoto() != null){
            ToolKit.removeNode(studentImageLabel);
            studentImageCircle.setFill(new ImagePattern(ToolKit.makeImage(faq.getStudent().getPhoto())));
        }
        else{
            studentImageLabel.setText(faq.getStudent().getShortName());
        }
        studentName.setText(faq.getStudent().getFullName());
        questionDate.setText(ToolKit.makeDateStructured(faq.getQuestionTime(), "hh:mm aa - dd MMMMM, yyyy"));
        question.setText(faq.getQuestion());
        if(faq.getAnswer() != null){
            ToolKit.removeNode(noAnswerLabel);
            ToolKit.removeNode(answerBtn);
            if(faq.getTeacher().getPhoto() != null){
                ToolKit.removeNode(teacherImageName);
                teacherImageCircle.setFill(new ImagePattern(ToolKit.makeImage(faq.getTeacher().getPhoto())));
            }
            else{
                teacherImageName.setText(faq.getTeacher().getShortName());
            }
            teacherName.setText(faq.getTeacher().getFullName());
            answerDate.setText(ToolKit.makeDateStructured(faq.getAnswerTime(), "hh:mm aa - dd MMMMM, yyyy"));
            answer.setText(faq.getAnswer());
        }
        else if(viewer == CurriculumController.ViewerType.OwnerTeacherNormal){
            ToolKit.removeNode(answerContainer);
            ToolKit.removeNode(noAnswerLabel);
            ToolKit.print(parent);
            connectAnswerInput();
        }
        else{
            ToolKit.removeNode(answerContainer);
            ToolKit.removeNode(answerBtn);
        }
    }
    private void connectAnswerInput() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/FAQInputBox.fxml"));
            ansInputPane = loader.load();
            ansInputCtrl = loader.<FAQInputBoxController>getController();
            ansInputCtrl.setParent(this);
            ansInputCtrl.setFAQ(faq);
            ansInputCtrl.makeEnvironmentForAnswerInput();
            answerBtn.setOnMouseClicked((event) -> {
                ansInputCtrl.show();
            });
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setViewer(CurriculumController.ViewerType viewer) {
        this.viewer = viewer;
    }
    
    public CourseDetailsController getParent(){
        return parent;
    }

    void setParent(CourseDetailsController parent) {
        this.parent = parent;
    }

    void removeAnswerBtn() {
        ToolKit.removeNode(answerBtn);
    }
}
