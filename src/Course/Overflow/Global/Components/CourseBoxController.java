/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.GLOBAL;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CourseBoxController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private ImageView picBox;
    @FXML
    private Label teacher;
    @FXML
    private Rating rating;
    @FXML
    private Label ratingValue;
    @FXML
    private Label numOfRating;
    @FXML
    private Label mainPrice;
    @FXML
    private Label offerPrice;
    @FXML
    private AnchorPane container;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rating.setUpdateOnHover(false);
        rating.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rating.setRating(Double.parseDouble(ratingValue.getText()));
            }
        });
        container.setOnMouseClicked((event)->{
            GLOBAL.PAGE_CTRL.setCoursePage();
        });
    }    
    
    public void setCourseTitle(String title){
        this.title.setText(title);
    }
}
