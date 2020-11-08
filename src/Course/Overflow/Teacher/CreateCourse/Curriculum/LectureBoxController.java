/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LectureBoxController implements Initializable {

      @FXML
      private Label lectureNameLabel;
      @FXML
      private FontAwesomeIconView cancelContents;
      @FXML
      private HBox availableContentContainer;
      @FXML
      private MaterialDesignIconView editIcon;
      @FXML
      private FontAwesomeIconView deleteIcon;
      private HBox lectureTitleContainer;
      @FXML
      private Label content;
      @FXML
      private AnchorPane lectureBoxPane;
      @FXML
      private FontAwesomeIconView up;
      @FXML
      private FontAwesomeIconView down;
      @FXML
      private CheckBox freeAvailableCkB;
      @FXML
      private Label lectureNo;
      private WeekBoxController parentController;
    private ContentsListBoxController contentListCtrl;
    private Week week;
    private Lecture lecture;

      
      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            lectureNameLabel.setMaxWidth(GLOBAL.LABEL_PREF_WIDTH - 420);
            availableContentContainer.setPadding(new Insets(0, GLOBAL.AVAILABLE_CONTENT_CON_PADDING, 0 ,GLOBAL.AVAILABLE_CONTENT_CON_PADDING));
            new ToolTip(MouseEvent.MOUSE_ENTERED, freeAvailableCkB, "If you want to set it as a preview of your course, check it.");
      }

      private void editClicked(MouseEvent event) {
            editIcon.visibleProperty().set(false);
            deleteIcon.visibleProperty().set(false);

            Label label = (Label) lectureTitleContainer.getChildren().get(0);
            label.visibleProperty().set(false);
            String lectureName = label.getText();

            TextField field = new TextField(lectureName);

            Button saveBtn = new Button("Save");
            saveBtn.setFocusTraversable(true);
            Button cancelBtn = new Button("Cancel");

            HBox hbox = new HBox(field, saveBtn, cancelBtn);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPadding(new Insets(0,0,0,10));
            lectureTitleContainer.getChildren().add(hbox);
            lectureTitleContainer.getChildren().remove(label);
            cancelBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                        lectureTitleContainer.getChildren().remove(hbox);
                        lectureTitleContainer.getChildren().add(label);
                        editIcon.visibleProperty().set(true);
                        deleteIcon.visibleProperty().set(true);
                        label.visibleProperty().set(true);
                  }
            });
            saveBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                        lectureTitleContainer.getChildren().remove(hbox);
                        lectureTitleContainer.getChildren().add(label);
                        label.setText(field.getText());
                        editIcon.visibleProperty().set(true);
                        deleteIcon.visibleProperty().set(true);
                        label.visibleProperty().set(true);
                  }
            });
      }

      private void deleteClicked(MouseEvent event) {
            VBox parent = (VBox) lectureBoxPane.getParent();
            parentController.removeLectureBoxController(parent.getChildren().indexOf(lectureBoxPane));
            parent.getChildren().remove(lectureBoxPane);
      }


      private void upClicked(MouseEvent event) {
            VBox parent = (VBox) lectureBoxPane.getParent();
            int idx = parent.getChildren().indexOf(lectureBoxPane);
            if (idx == 0) {
                  return;
            }
            AnchorPane upperBox = (AnchorPane) parent.getChildren().get(idx - 1);
            AnchorPane bottomBox = (AnchorPane) parent.getChildren().get(idx);
            parent.getChildren().removeAll(upperBox, bottomBox);
            parent.getChildren().add(idx - 1, bottomBox);
            parent.getChildren().add(idx, upperBox);
      }

      private void downClicked(MouseEvent event) {
            VBox parent = (VBox) lectureBoxPane.getParent();
            int idx = parent.getChildren().indexOf(lectureBoxPane);
            if (idx == parent.getChildren().size() - 1) {
                  return;
            }
            AnchorPane upperBox = (AnchorPane) parent.getChildren().get(idx);
            AnchorPane bottomBox = (AnchorPane) parent.getChildren().get(idx + 1);
            parent.getChildren().removeAll(upperBox, bottomBox);
            parent.getChildren().add(idx, bottomBox);
            parent.getChildren().add(idx + 1, upperBox);
      }

      @FXML
      private void mouseClicked(MouseEvent event) throws IOException {
            Object src = event.getSource();
            if(src == editIcon){
                  HBox parent = (HBox) lectureNameLabel.getParent();
                  editIcon.setVisible(false);
                  deleteIcon.setVisible(false);
                  parent.getChildren().remove(lectureNameLabel);
                  
                  TextField field = new TextField(lectureNameLabel.getText());
                  Button saveBtn = new Button("Save");
                  Button cancelBtn = new Button("Cancel");
                  HBox hbox = new HBox(field,saveBtn,cancelBtn);
                  hbox.setSpacing(10);
                  parent.getChildren().add(hbox);
                  
                  Button[] btns = new Button[]{saveBtn, cancelBtn};
                  
                  for(Button btn : btns){
                        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                              @Override
                              public void handle(MouseEvent event) {
                                    System.out.println("hoitese");
                                    parent.getChildren().remove(hbox);
                                    if(event.getSource()==saveBtn){
                                          lectureNameLabel.setText(field.getText());
                                    }
                                    editIcon.visibleProperty().set(true);
                                    deleteIcon.visibleProperty().set(true);
                                    parent.getChildren().add(lectureNameLabel);
                              }
                        });
                  }
            } else if(src == up || src == down){
                  VBox parent = (VBox) lectureBoxPane.getParent();
                  int idx = parent.getChildren().indexOf(lectureBoxPane);
                  parentController.swapControllers(idx, idx + (src==up ? -1 : 1));
                  ToolKit.moveRow(parent, idx, src==up ? -1 : 1);
            } else if(src == deleteIcon){
                  VBox parent = (VBox) lectureBoxPane.getParent();
                  parent.getChildren().remove(lectureBoxPane);
            } else if(src == content){
                  content.setVisible(false);
                  cancelContents.setVisible(true);
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ContentsListBox.fxml"));
                  AnchorPane pane;
                  pane = (AnchorPane) loader.load();
                  contentListCtrl = loader.<ContentsListBoxController>getController();
                  contentListCtrl.setParent(this);
                  availableContentContainer.getChildren().add(pane);
            } else if(src == cancelContents){
                  content.setVisible(true);
                  cancelContents.setVisible(false);
                  availableContentContainer.getChildren().clear();
            }
      }
      public void setContentVisible(boolean b){
            content.setVisible(b);
      }
      public void setCancelVisible(boolean b){
            cancelContents.setVisible(b);
      }
      public HBox getAvailableContentContainer(){
            return availableContentContainer;
      }
      public void setLectureNo(int x){
            lectureNo.setText(""+x);
      }

      void setParent(WeekBoxController parentController) {
            this.parentController = parentController;
      }
      
    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Lecture uploadToDB() {
        lecture = new Lecture(Integer.parseInt(lectureNo.getText()), lectureNameLabel.getText(), contentListCtrl.uploadToDb(), freeAvailableCkB.isSelected(), week);
        return lecture;
    }
}
