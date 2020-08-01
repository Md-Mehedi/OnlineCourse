/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.Layout.FloatingPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CheckoutPageController extends FloatingPane implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private TextField cardNameField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expireField;
    @FXML
    private Label discountPrice;
    @FXML
    private Label totalPrice;
    @FXML
    private Label originalPrice;
    @FXML
    private Label completeBtn;
    @FXML
    private ImageView coursePhoto;
    @FXML
    private Label courseName;
    @FXML
    private Label originalPriceBottom;
    @FXML
    private Label discountsPriceBottom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backPane = container;
        addListener();
        
    }    

    private void addListener() {
        completeBtn.setOnMouseClicked((event) -> {
            close();
        });
    }
    
}
