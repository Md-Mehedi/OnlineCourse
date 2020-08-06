/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.TargetStudentPage;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Customize.ToolTip;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TargetStudentPageController implements Initializable {

      @FXML
      private VBox courseLearningContainer;
      @FXML
      private VBox requContainer;
      @FXML
      private VBox propertiesContainer;
      
      AnchorPane tempPane;
      @FXML
      private AnchorPane container;
      private AnchorPane mainContainer;
      @FXML
      private Label addLearning;
      @FXML
      private Label addRequirement;
      @FXML
      private Label addProperties;
      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            mainContainer = container;
            try {
                  FXMLLoader loader =new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
                  tempPane = loader.load();
                  loader.<AddAnswerController>getController().setParent(this, courseLearningContainer, mainContainer);
                  courseLearningContainer.getChildren().add(tempPane);
                  loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
                  tempPane = loader.load();
                  loader.<AddAnswerController>getController().setParent(this, requContainer, mainContainer);
                  requContainer.getChildren().add(tempPane);
                  loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/Properties.fxml"));
                  tempPane = loader.load();
                  loader.<PropertiesController>getController().setParent(this, propertiesContainer, mainContainer);
                  propertiesContainer.getChildren().add(tempPane);
            } catch (IOException ex) {
                  Logger.getLogger(TargetStudentPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            ToolTip.invisibleAll();
            if(src == addLearning){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
                  AnchorPane pane = loader.load();
                  loader.<AddAnswerController>getController().setParent(this, courseLearningContainer, mainContainer);
                  courseLearningContainer.getChildren().add(pane);
            } else if(src == addRequirement){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/AddAnswer.fxml"));
                  AnchorPane pane = loader.load();
                  loader.<AddAnswerController>getController().setParent(this, requContainer, mainContainer);
                  requContainer.getChildren().add(pane);
            } else if(src == addProperties){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/Properties.fxml"));
                  AnchorPane pane = loader.load();
                  loader.<PropertiesController>getController().setParent(this, propertiesContainer, mainContainer);
                  propertiesContainer.getChildren().add(pane);
            }
      }

      public AnchorPane getContainer() {
            return container;
      }
      
}
