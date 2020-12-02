/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Communication.MessagePage;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Layout.PageByPageLayoutController.BoxViewType;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Student.Student;
import Course.Overflow.Teacher.Teacher;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PersonPreviewController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label numOfStudents;
    @FXML
    private Label numOfReviews;
    @FXML
    private Label description;
    private GridPane grid;
    @FXML
    private ImageView photo;
    @FXML
    private VBox linkBox;
    private FXMLLoader loader;
    @FXML
    private AnchorPane gridContainer;
    private PageByPageLayoutController gridCtrl;
    private Person person;
    @FXML
    private Label numOfRating;
    @FXML
    private Rating rating;
    @FXML
    private Label instituition;
    @FXML
    private Label email;
    @FXML
    private Label fbURL;
    @FXML
    private Label website;
    @FXML
    private Label youtubeURL;
    @FXML
    private Label linkedInURL;
    @FXML
    private Label reviewLabel;
    @FXML
    private VBox ratingContainer;
    @FXML
    private Label studentLabel;
    @FXML
    private HBox topContainer;
    @FXML
    private VBox courseContainer;
    @FXML
    private JFXButton sendMessageBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
    }    
    
    private void  addCourses(Person person){
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            gridContainer.getChildren().add(loader.load());
            gridCtrl = loader.<PageByPageLayoutController>getController();
            ArrayList<Course> list = null;
            if(person.getAccountType() == Person.AccountType.Teacher){
                list = ((Teacher)person).getCourses();
            }
            else if(person.accountType == Person.AccountType.Student){
                list = ((Student)person).getCourses();
            }
            if(list.size() == 0){
                ToolKit.removeNode(courseContainer);
            }
            else gridCtrl.setUpPage(list, BoxViewType.GridView, 3);
        } catch (IOException ex) {
            Logger.getLogger(PersonPreviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadData(Person person){
        this.person = person;
        name.setText(person.getFullName());
        photo.setImage(person.getImage());
        if(person.getInstitution().equals("")){
            ToolKit.removeNode(instituition.getParent());
        }
        else instituition.setText(person.getInstitution());
        email.setText(person.getEmail());
        if(person.getFbURL().equals("")){
            ToolKit.removeNode(fbURL.getParent());
        }
        else fbURL.setText(person.getFbURL());
        if(person.getWebsite().equals("")){
            ToolKit.removeNode(website.getParent());
        }
        else website.setText(person.getWebsite());
        if(person.getYoutubeURL().equals("")){
            ToolKit.removeNode(youtubeURL.getParent());
        }
        else youtubeURL.setText(person.getYoutubeURL());
        if(person.getLinkedInURL().equals("")){
            ToolKit.removeNode(linkedInURL.getParent());
        }
        else linkedInURL.setText(person.getLinkedInURL());
        description.setText(person.getAbout());
        
        if(person.getAccountType() == Person.AccountType.Teacher){
            addTeacherProperties();
        }
        else if(person.getAccountType() == Person.AccountType.Student){
            addStudentProperties();
        }
        else if(person.getAccountType() == Person.AccountType.Admin){
            addAdminProperties();
        }
        if(person.getUsername().equals(ToolKit.getCurrentPerson().getUsername())){
            ToolKit.removeNode(sendMessageBtn);
        }
    }

    private void addTeacherProperties() {
        Teacher teacher = new Teacher(person.getUsername());
        numOfStudents.setText(teacher.getNumOfStudent().toString());
        numOfReviews.setText(teacher.getNumOfReview().toString());
        Double ratingValue = teacher.getRating();
        numOfRating.setText(teacher.getNumOfRating().toString());
        rating.setRating(ratingValue);
        rating.setOnMouseClicked((event)->{
            rating.setRating(ratingValue);
        });
        addCourses(teacher);
    }

    private void addStudentProperties() {
        Student student = new Student(person.getUsername());
        numOfReviews.setText(student.getNumOfReview().toString());
        reviewLabel.setText("Reviewed");
        ToolKit.removeNode(ratingContainer);
        Integer numOfCourse = student.getNumOfCourse();
        numOfStudents.setText(numOfCourse.toString());
        studentLabel.setText(numOfCourse > 1 ? "Bought Courses" : "Bought Course");
        addCourses(student);
    }

    private void addAdminProperties() {
        ToolKit.removeNode(courseContainer);
        ToolKit.removeNode(topContainer);
    }

    private void addListener() {
        sendMessageBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Messenger);
            MessagePage ctrl = (MessagePage) GLOBAL.PAGE_CTRL.getPage();
            ctrl.addNewChatHead(person);
        });
    }
    
    
        
    
}
