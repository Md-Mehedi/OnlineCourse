/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Pricing;

import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PricingController implements Initializable {

      @FXML
      private TextField price;
      @FXML
      private CheckBox offerCkBox;
      @FXML
      private HBox offerContainer;
      @FXML
      private CheckBox promoCkBox;
      @FXML
      private HBox promoContainer;
      
      private AnchorPane promoPane;
      private AnchorPane offerPane;
      @FXML
      private AnchorPane container;
      @FXML
      private Label pricingDetailLabel;
      @FXML
      private FontAwesomeIconView infoPrice;
    @FXML
    private JFXButton uploadBtn;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            new ToolTip(MouseEvent.MOUSE_ENTERED, infoPrice,"If you want to give this course as free, please set the 'Course price' as 0.");
      }      

      @FXML
      private void mouseExited(MouseEvent event) {
      }

      @FXML
      private void mouseEntered(MouseEvent event) {
      }

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException {
            Object src = event.getSource();
            if(src == offerCkBox){
                  if(offerCkBox.isSelected()){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_PRICING_LOCATION + "/Offer.fxml"));
                        offerPane = loader.load();
                        offerContainer.getChildren().add(offerPane);
                        loader.<OfferController>getController().setParent(this, container);
                  } else {
                        offerContainer.getChildren().removeAll(offerPane);
                  }
            } else if(src == promoCkBox){
                  if(promoCkBox.isSelected()){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_PRICING_LOCATION + "/Promocode.fxml"));
                        promoPane = loader.load();
                        promoContainer.getChildren().addAll(promoPane);
                        loader.<PromocodeController>getController().setParent(this, container);
                        
                  } else {
                        promoContainer.getChildren().removeAll(promoPane);
                  }
            }
      }
      
}
