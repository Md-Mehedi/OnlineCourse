/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Layout.PageByPageLayoutController.BoxType;
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

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TeacherPreviewController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label shortDetails;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addCourses();
    }    
    
    private void  addCourses(){
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            gridContainer.getChildren().add(loader.load());
            gridCtrl = loader.<PageByPageLayoutController>getController();
            gridCtrl.setUpPage(BoxType.CourseGrid, 13, 3, 22);
        } catch (IOException ex) {
            Logger.getLogger(TeacherPreviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
}
