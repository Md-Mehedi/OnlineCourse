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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ArticleInputBoxController implements Initializable {

      private LectureBoxController parent;
      private boolean isNew;
      @FXML
      private AnchorPane container;
      @FXML
      private TextArea article;
      @FXML
      private Button saveBtn;
      @FXML
      private Button cancelBtn;
      @FXML
      private TextField title;
      private String oldArticle;
      private String oldTitle;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            // TODO
            //Tools.makeDynamicTextArea(article);
      }      

      void setParent(LectureBoxController parent, boolean isNew) {
            this.parent = parent;
            this.isNew = isNew;
      }

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException{
            Object src = event.getSource();
            if(src == saveBtn || (src==cancelBtn && isNew == false)){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ArticleOutputBox.fxml"));
                  AnchorPane pane = loader.load();
                  ArticleOutputBoxController ctrl = loader.getController();
                  ctrl.setParent(parent);
                  parent.getAvailableContentContainer().getChildren().add(pane);
                  parent.getAvailableContentContainer().getChildren().remove(container);
                  parent.setCancelVisible(false);
                  if(src != saveBtn && isNew == false){
                        title.setText(oldTitle);
                        article.setText(oldArticle);
                  }
                  ctrl.setTitle(title.getText());
                  ctrl.setArticle(article.getText());
            } else if(src == cancelBtn && isNew == true){
                  parent.getAvailableContentContainer().getChildren().remove(container);
                  parent.setCancelVisible(false);
                  parent.setContentVisible(true);
            }
      }

      void setArticle(String text) {
            article.setText(text);
            oldArticle = text;
      }

      void setTitle(String text) {
            title.setText(text);
            oldTitle = text;
      }
      
}
