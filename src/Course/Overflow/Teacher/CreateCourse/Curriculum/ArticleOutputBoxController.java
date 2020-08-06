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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ArticleOutputBoxController implements Initializable {

      private LectureBoxController parent;
      @FXML
      private Label title;
      @FXML
      private Label article;
      @FXML
      private Button edit;
      @FXML
      private AnchorPane container;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            article.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH);
            title.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH);
      }      
      
      public void setParent(LectureBoxController parent){
            this.parent = parent;
      }

      void setTitle(String text) {
            title.setText(text);
      }

      void setArticle (String text){
            article.setText(text);
      }

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException {
            if(event.getSource() == edit){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ArticleInputBox.fxml"));
                  AnchorPane pane = loader.load();
                  ArticleInputBoxController ctrl = loader.getController();
                  ctrl.setParent(parent,false);
                  parent.getAvailableContentContainer().getChildren().remove(container);
                  parent.getAvailableContentContainer().getChildren().add(pane);
                  ctrl.setArticle(article.getText());
                  ctrl.setTitle(title.getText());
            }
      }      
}
