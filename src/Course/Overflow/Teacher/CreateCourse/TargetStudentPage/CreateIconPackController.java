/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.TargetStudentPage;

import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CreateIconPackController implements Initializable {

      @FXML
      private Label upBtn;
      private String iconName;
      private PropertiesController parentController;
      private AnchorPane parentPane;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            new ToolTip(MouseEvent.MOUSE_ENTERED, upBtn, "Upload your custom icon in .png format and 25x25 size ratio");
      }      

      @FXML
      private void mouseClicked(MouseEvent event) throws FileNotFoundException {
            Object src = event.getSource();
            if(src == upBtn){
                  File file = ToolKit.chooseFile("image");
                  parentController.setIconPackVisibility(false);
                  parentController.setIconPic(file);
            } else {
                  FontAwesomeIconView selectIcon = (FontAwesomeIconView) src;
                  iconName = selectIcon.getGlyphName();
                  parentController.setIcon(iconName);
                  parentController.setIconPackVisibility(false);
            }
      }
      
      public void setParent(PropertiesController parentController, AnchorPane parentPane){
            this.parentController = parentController;
            this.parentPane = parentPane;
      }
      
}
