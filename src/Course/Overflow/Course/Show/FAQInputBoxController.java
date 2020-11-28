/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Course.Show;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.FAQ;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.FloatingPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FAQInputBoxController extends FloatingPane implements Initializable {

    @FXML
    private JFXTextArea questionField;
    @FXML
    private JFXButton faqSubmitBtn;
    @FXML
    private JFXButton faqCancelBtn;
    private CourseDetailsController courseDetailsParent;
    private Course course;
    @FXML
    private AnchorPane rootPane;
    private FAQOutputBoxController faqOutputParent;
    private FAQ faq;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.backPane = rootPane;
        addListener();
    }    

    void setParent(CourseDetailsController parent) {
        this.courseDetailsParent = parent;
    }
    
    void setParent(FAQOutputBoxController parent) {
        this.faqOutputParent = parent;
    }

    void setCourse(Course course) {
        this.course = course;
    }
    
    private void addListener() {
        faqSubmitBtn.setOnMouseClicked((event) -> {
            FAQ faq = new FAQ(course, GLOBAL.STUDENT, questionField.getText());
            questionField.setText("");
            courseDetailsParent.addFAQBox(faq);
            close();
        });
        faqCancelBtn.setOnMouseClicked((event) -> {
            close();
        });
    }

    void makeEnvironmentForAnswerInput() {
        questionField.setPromptText("Write your answer : ");
        faqSubmitBtn.setOnMouseClicked((event) -> {
            faq.setAnswer(questionField.getText());
            questionField.setText("");
            faqOutputParent.removeAnswerBtn();
            faqOutputParent.loadAnswer(faq);
            close();
        });
    }

    void setFAQ(FAQ faq) {
        this.faq = faq;
    }
    
}
