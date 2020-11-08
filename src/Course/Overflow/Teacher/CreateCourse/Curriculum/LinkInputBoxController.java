/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LinkInputBoxController implements Initializable {

      private LectureBoxController parent;
      private String oldLink;
      private String oldLinkDesc;
      
      private boolean isNew;
      @FXML
      private TextArea linkDescField;
      @FXML
      private TextField linkField;
      @FXML
      private AnchorPane container;
      @FXML
      private Button saveBtn;
      @FXML
      private Button cancelBtn;
     
      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            //Tools.makeDynamicTextArea(linkDescField);
      }      

      void setParent(LectureBoxController parent, boolean isNew) {
            this.parent = parent;
            this.isNew = isNew;
      }

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException {
            Object src = event.getSource();
            if(src == saveBtn || (src == cancelBtn && isNew == false)){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/LinkOutputBox.fxml"));
                  AnchorPane pane = loader.load();
                  LinkOutputBoxController ctrl = loader.getController();
                  ctrl.setParent(parent);
                  if(src != saveBtn && isNew == false){
                        linkDescField.setText(oldLinkDesc);
                        linkField.setText(oldLink);
                  }
                  ctrl.setLink(linkField.getText());
                  ctrl.setLinkDesc(linkDescField.getText());
                  parent.getAvailableContentContainer().getChildren().remove(container);
                  parent.getAvailableContentContainer().getChildren().add(pane);
                  parent.setCancelVisible(false);
            } else if(src == cancelBtn){
                  parent.getAvailableContentContainer().getChildren().remove(container);
                  parent.setCancelVisible(false);
                  parent.setContentVisible(true);
            }
      }

      void setLink(String text) {
            linkField.setText(text);
            oldLink = text;
      }

      void setLinkDetails(String text) {
            linkDescField.setText(text);
            oldLinkDesc = text;
      }
      
      public Files uploadToDB(){
          return new Files(FileType.toType("Link"), linkDescField.getText(), linkField.getText());
      }
      
}
