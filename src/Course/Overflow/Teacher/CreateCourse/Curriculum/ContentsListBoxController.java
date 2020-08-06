/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ContentsListBoxController implements Initializable {

      @FXML
      private VBox articleBtn;
      @FXML
      private VBox videoBtn;
      @FXML
      private VBox pdfBtn;
      @FXML
      private VBox linkBtn;
      @FXML
      private AnchorPane container;

      private LectureBoxController parent;
      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            // TODO
      }      

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException {
            Object src = event.getSource();
            HBox box = (HBox) container.getParent();
            AnchorPane pane = new AnchorPane();
            if(src == articleBtn){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ArticleInputBox.fxml"));
                  pane = loader.load();
                  loader.<ArticleInputBoxController>getController().setParent(parent,true);
            } else if(src == videoBtn){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/CourseContentsUploader.fxml"));
                  pane = loader.load();
                  loader.<CourseContentsUploaderController>getController().setParent(parent,"video",true);
            } else if(src == pdfBtn){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/CourseContentsUploader.fxml"));
                  pane = loader.load();
                  loader.<CourseContentsUploaderController>getController().setParent(parent,"pdf",true);
            } else if(src == linkBtn){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/LinkInputBox.fxml"));
                  pane = loader.load();
                  loader.<LinkInputBoxController>getController().setParent(parent,true);
            }
            box.getChildren().remove(container);
            box.getChildren().add(pane);
      }
      
      public void setParent(LectureBoxController parent){
            this.parent = parent;
      }
      
}
