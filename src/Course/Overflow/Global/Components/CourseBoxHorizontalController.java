/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.GLOBAL;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        container.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.setCoursePage();
        });
    }    

    public void addPriceAndPurchaseDateColumn() {
        Label label1 = new Label("Price");
        Label label2 = new Label("12000");
        label2.setStyle("-fx-font-weight: bold;");
        
        VBox box = new VBox(label1, label2);
        box.setStyle("-fx-alignment: center-left;");
        box.setMinWidth(90);
        
        hContainer.getChildren().remove(2);
        hContainer.getChildren().add(box);
        
        label1 = new Label("Purchased on");
        label2 = new Label("22 September, 2020");
        label2.setStyle("-fx-font-weight: bold;");
        
        box = new VBox(label1, label2);
        box.setStyle("-fx-alignment: center-left;");
        box.setMinWidth(190);
        
        hContainer.getChildren().add(box);
    }
    
}
