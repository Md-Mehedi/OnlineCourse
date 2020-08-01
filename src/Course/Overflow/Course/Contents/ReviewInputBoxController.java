/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Course.Contents;

import Course.Overflow.Global.Layout.FloatingPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ReviewInputBoxController extends FloatingPane implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private Rating rating;
    @FXML
    private TextArea reviewField;
    @FXML
    private Label submtBtn;
    @FXML
    private Label cancelBtn;
    
    private AnchorPane parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.backPane = container;
        addListener();
    } 

    private void addListener() {
        cancelBtn.setOnMouseClicked((event) -> {
            close();
        });
        submtBtn.setOnMouseClicked((event) -> {
            close();
        });
    }
    
    
}
