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
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController.ViewerType;
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
import javafx.scene.image.Image;
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
    private FAQInputBoxController ansInputCtrl;
    private AnchorPane ansInputPane;
    @FXML
    private VBox questionContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void loadData(ViewerType viewer, FAQ faq) {
        this.viewer = viewer;
        this.faq = faq;
        if(faq.getStudent().getPhoto() != null){
            ToolKit.removeNode(studentImageLabel);
            studentImageCircle.setFill(new ImagePattern(new Image(faq.getStudent().getPhoto().getContent())));
        }
        else{
            studentImageLabel.setText(faq.getStudent().getShortName());
        }
        studentName.setText(faq.getStudent().getFullName());
        questionDate.setText(ToolKit.makeDateStructured(faq.getQuestionTime(), "hh:mm aa - dd MMMMM, yyyy"));
        question.setText(faq.getQuestion());
        if(faq.getAnswer() != null){
            loadAnswer(faq);
        }
        else if(viewer == CurriculumController.ViewerType.OwnerTeacherNormal){
            ToolKit.removeNode(answerContainer);
            ToolKit.removeNode(noAnswerLabel);
            connectAnswerInput();
        }
        else{
            ToolKit.removeNode(answerContainer);
            ToolKit.removeNode(answerBtn);
        }
    }
    
    public void loadAnswer(FAQ faq) {
        if(noAnswerLabel.getParent() != null) ToolKit.removeNode(noAnswerLabel);
        if(answerBtn.getParent() != null) ToolKit.removeNode(answerBtn);
        if(faq.getTeacher().getPhoto() != null){
            ToolKit.removeNode(teacherImageName);
            teacherImageCircle.setFill(new ImagePattern(new Image(faq.getTeacher().getPhoto().getContent())));
        }
        else{
            teacherImageName.setText(faq.getTeacher().getShortName());
        }
        teacherName.setText(faq.getTeacher().getFullName());
        answerDate.setText(ToolKit.makeDateStructured(faq.getAnswerTime(), "hh:mm aa - dd MMMMM, yyyy"));
        answer.setText(faq.getAnswer());
        if(!questionContainer.getChildren().contains(answerContainer)) questionContainer.getChildren().add(answerContainer);
    }
    
    private void connectAnswerInput() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/FAQInputBox.fxml"));
            ansInputPane = (AnchorPane) loader.load();
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

    void removeAnswerBtn() {
        ToolKit.removeNode(answerBtn);
    }
    public Label getQuestionLabel(){
        return question;
    }
}
