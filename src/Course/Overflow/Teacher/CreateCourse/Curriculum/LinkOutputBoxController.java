/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LinkOutputBoxController implements Initializable {

      @FXML
      private AnchorPane container;
      @FXML
      private Label linkDetails;
      @FXML
      private Label link;
      @FXML
      private Button editBtn;
      private LectureBoxController parent;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            link.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH-120);
            linkDetails.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH);
      }      

      void setLink(String text) {
            link.setText(text);
      }

      void setLinkDesc(String text) {
            linkDetails.setText(text);
      }

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException {
            if(event.getSource() == editBtn){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/LinkInputBox.fxml"));
                  AnchorPane pane = loader.load();
                  LinkInputBoxController ctrl = loader.getController();
                  ctrl.setParent(parent, false);
                  ctrl.setLink(link.getText());
                  ctrl.setLinkDetails(linkDetails.getText());
                  parent.getAvailableContentContainer().getChildren().remove(container);
                  parent.getAvailableContentContainer().getChildren().add(pane);
            } else if(event.getSource() == link){
                  ToolKit.openLink(link.getText());
            }
      }
      
      public void setParent(LectureBoxController parent){
            this.parent = parent;
      }
      
}
