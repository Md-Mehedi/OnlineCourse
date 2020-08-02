/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CourseBoxHorizontalController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private HBox hContainer;
    @FXML
    private ImageView coursePhoto;
    @FXML
    private Label courseName;
    @FXML
    private Label courseShortDesc;
    @FXML
    private Label courseInst;
    @FXML
    private Label rating;
    @FXML
    private Rating ratingStar;
    @FXML
    private Label numOfRating;
    @FXML
    private Label numOfLecture;
    @FXML
    private Label level;
    @FXML
    private Label curPrice;
    @FXML
    private Label mainPrice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("In Course Box Horizontal");
    }    
    
}
