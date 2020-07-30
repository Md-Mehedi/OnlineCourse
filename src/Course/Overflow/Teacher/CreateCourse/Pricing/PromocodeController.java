/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Pricing;

import Course.Overflow.Global.Customize.ToolTip;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PromocodeController implements Initializable {

      @FXML
      private TextField promoField;
      @FXML
      private DatePicker startDate;
      @FXML
      private DatePicker endDate;
      @FXML
      private FontAwesomeIconView infoStartDate;
      @FXML
      private FontAwesomeIconView infoEndDate;
      private AnchorPane parentContainer;
      private PricingController parentController;
    @FXML
    private TextField promoOff;
    @FXML
    private FontAwesomeIconView infoOff;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            new ToolTip(MouseEvent.MOUSE_ENTERED,infoStartDate, "From this date promo code will be available.");
            new ToolTip(MouseEvent.MOUSE_ENTERED, infoEndDate, "Promocode will be available to this date.");
            new ToolTip(MouseEvent.MOUSE_ENTERED, infoOff, "You can offer a percentage over your course price and share this promocode to give extra discount.");
      }      
      
      public void setParent(PricingController parentController, AnchorPane parentConatiner){
            this.parentController = parentController;
            this.parentContainer = parentConatiner;
      }
}
