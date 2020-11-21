/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Layout.PageByPageLayoutController.BoxViewType;
import Course.Overflow.Global.ToolKit;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TeacherPreviewController implements Initializable {

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
    private Teacher teacher;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    private void  addCourses(){
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            gridContainer.getChildren().add(loader.load());
            gridCtrl = loader.<PageByPageLayoutController>getController();
            gridCtrl.setUpPage(teacher.getCourses(), BoxViewType.GridView, 3);
        } catch (IOException ex) {
            Logger.getLogger(TeacherPreviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadData(Teacher teacher){
        this.teacher = teacher;
        name.setText(teacher.getFullName());
        numOfStudents.setText(teacher.getNumOfStudent().toString());
        numOfReviews.setText(teacher.getNumOfReview().toString());
        Double ratingValue = teacher.getRating();
        numOfRating.setText(teacher.getNumOfRating().toString());
        rating.setRating(ratingValue);
        rating.setOnMouseClicked((event)->{
            rating.setRating(ratingValue);
        });
        photo.setImage(teacher.getImage());
        if(teacher.getInstitution().equals("")){
            ToolKit.removeNode(instituition.getParent());
        }
        else instituition.setText(teacher.getInstitution());
        email.setText(teacher.getEmail());
        if(teacher.getFbURL().equals("")){
            ToolKit.removeNode(fbURL.getParent());
        }
        else fbURL.setText(teacher.getFbURL());
        if(teacher.getWebsite().equals("")){
            ToolKit.removeNode(website.getParent());
        }
        else website.setText(teacher.getWebsite());
        if(teacher.getYoutubeURL().equals("")){
            ToolKit.removeNode(youtubeURL.getParent());
        }
        else youtubeURL.setText(teacher.getYoutubeURL());
        if(teacher.getLinkedInURL().equals("")){
            ToolKit.removeNode(linkedInURL.getParent());
        }
        else linkedInURL.setText(teacher.getLinkedInURL());
        
        
        description.setText(teacher.getAbout());
        addCourses();
    }
    
    
        
    
}
