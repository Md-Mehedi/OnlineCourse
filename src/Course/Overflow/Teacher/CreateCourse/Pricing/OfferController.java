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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class OfferController implements Initializable {

      private AnchorPane parentContainer;
      private PricingController parentController;
      @FXML
      private FontAwesomeIconView infoOffer;
      @FXML
      private TextField offerField;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            new ToolTip(MouseEvent.MOUSE_ENTERED, infoOffer, "You can offer your student a percantage off.");
      }      
      
      public void setParent(PricingController parentController, AnchorPane parentConatiner){
            this.parentController = parentController;
            this.parentContainer = parentConatiner;
      }
}
