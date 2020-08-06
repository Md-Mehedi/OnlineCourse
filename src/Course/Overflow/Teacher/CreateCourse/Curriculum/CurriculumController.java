/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Customize.ToolTip;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CurriculumController implements Initializable {

      @FXML
      private AnchorPane container;
      @FXML
      private VBox weekBoxContainer;
      @FXML
      private ImageView addWeekBtn;
      
      private ArrayList<WeekBoxController> weekBoxControllers;
      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb){
            weekBoxControllers = new ArrayList<WeekBoxController>();
            try {
                  AnchorPane root = new AnchorPane();
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/WeekBox.fxml"));
                  root = (AnchorPane) loader.load();
                  loader.<WeekBoxController>getController().setParent(this, container);
                  weekBoxControllers.add(loader.<WeekBoxController>getController());
                  weekBoxContainer.getChildren().add(root);
            } catch (IOException ex) {Logger.getLogger(CurriculumController.class.getName()).log(Level.SEVERE, null, ex);}
            new ToolTip(MouseEvent.MOUSE_ENTERED, addWeekBtn, "Add more weeks.");
      }      

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException {
            if(event.getSource() == addWeekBtn){
                  AnchorPane root = new AnchorPane();
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/WeekBox.fxml"));
                  root = (AnchorPane) loader.load();
                  loader.<WeekBoxController>getController().setParent(this, container);
                  loader.<WeekBoxController>getController().setWeekNumber(weekBoxContainer.getChildren().size()+1);
                  weekBoxControllers.add(loader.<WeekBoxController>getController());
                  weekBoxContainer.getChildren().add(root);
            }
      }
      
      public void swapControllers(int idx1, int idx2){
            if(idx1 == -1 || idx2 == -1) return;
            if(idx1 == weekBoxControllers.size() || idx2 == weekBoxControllers.size()) return;
            
            weekBoxControllers.get(idx1).setWeekNumber(idx2+1);
            weekBoxControllers.get(idx2).setWeekNumber(idx1+1);
            WeekBoxController removed = weekBoxControllers.get(Math.max(idx1, idx2));
            weekBoxControllers.remove(idx1<idx2 ? idx2 : idx1);
            weekBoxControllers.add(Math.min(idx1, idx2), removed);
      }
      
      public void removeWeekBoxController(int idx){
            weekBoxControllers.remove(idx);
            for(int i=0;i<weekBoxControllers.size();i++){
                  weekBoxControllers.get(i).setWeekNumber(i+1);
            }
      }
}
