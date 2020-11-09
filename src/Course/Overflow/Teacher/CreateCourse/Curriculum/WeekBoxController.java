/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class WeekBoxController implements Initializable {

    @FXML
    private VBox lectureBoxContainer;
    @FXML
    private MaterialDesignIconView editIcon;
    @FXML
    private FontAwesomeIconView deleteIcon;
    @FXML
    private ImageView addIcon;
    @FXML
    private AnchorPane weekBoxPane;
    @FXML
    private FontAwesomeIconView up;
    @FXML
    private FontAwesomeIconView down;
    @FXML
    private Label weekNo;
    @FXML
    private Label weekNameLabel;
    private CurriculumController parentController;
    private AnchorPane parentPane;
    private ArrayList<LectureBoxController> lectureBoxControllers;
    private ArrayList<Lecture> lectures;
    private Week week;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lectureBoxControllers = new ArrayList<>();
        lectures = new ArrayList<>();
        weekNameLabel.setMaxWidth(GLOBAL.LABEL_PREF_WIDTH - 230);
        lectureBoxContainer.setPadding(new Insets(10, GLOBAL.LEC_BOX_CON_RIGHT_PADDING, 10, GLOBAL.LEC_BOX_CON_LEFT_PADDING));
        new ToolTip(MouseEvent.MOUSE_ENTERED, addIcon, "Add more lectures.");
    }

    public VBox getLectureBoxContainer() {
        return lectureBoxContainer;
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        Object src = event.getSource();
        if (src == editIcon) {
            HBox parent = (HBox) weekNameLabel.getParent();
            editIcon.setVisible(false);
            deleteIcon.setVisible(false);
            parent.getChildren().remove(weekNameLabel);

            TextField field = new TextField(weekNameLabel.getText());
            Button saveBtn = new Button("Save");
            Button cancelBtn = new Button("Cancel");
            HBox hbox = new HBox(field, saveBtn, cancelBtn);
            hbox.setSpacing(10);
            parent.getChildren().add(hbox);

            Button[] btns = new Button[]{saveBtn, cancelBtn};

            for (Button btn : btns) {
                btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        parent.getChildren().remove(hbox);
                        if (event.getSource() == saveBtn) {
                            weekNameLabel.setText(field.getText());
                        }
                        editIcon.visibleProperty().set(true);
                        deleteIcon.visibleProperty().set(true);
                        parent.getChildren().add(weekNameLabel);
                    }
                });
            }
        } else if (src == up || src == down) {
            VBox parent = (VBox) weekBoxPane.getParent();
            int idx = parent.getChildren().indexOf(weekBoxPane);
            parentController.swapControllers(idx, idx + (src == up ? -1 : 1));
            ToolKit.moveRow(parent, idx, src == up ? -1 : 1);
        } else if (src == deleteIcon) {
            VBox parent = (VBox) weekBoxPane.getParent();
            parentController.removeWeekBoxController(parent.getChildren().indexOf(weekBoxPane));
            parent.getChildren().remove(weekBoxPane);
        } else if (src == addIcon) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/LectureBox.fxml"));
            AnchorPane pane;
            try {
                pane = (AnchorPane) loader.load();
                loader.<LectureBoxController>getController().setParent(this);
                loader.<LectureBoxController>getController().setLectureNo(lectureBoxContainer.getChildren().size() + 1);
                lectureBoxControllers.add(loader.<LectureBoxController>getController());
                lectureBoxContainer.getChildren().add(pane);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public void setParent(CurriculumController parentController, AnchorPane parentPane) {
        this.parentController = parentController;
        this.parentPane = parentPane;
    }

    public void setWeekNumber(int x) {
        weekNo.setText("" + x);
    }

    public void swapControllers(int idx1, int idx2) {
        if (idx1 == -1 || idx2 == -1) {
            return;
        }
        if (idx1 == lectureBoxControllers.size() || idx2 == lectureBoxControllers.size()) {
            return;
        }

        lectureBoxControllers.get(idx1).setLectureNo(idx2 + 1);
        lectureBoxControllers.get(idx2).setLectureNo(idx1 + 1);
        LectureBoxController removed = lectureBoxControllers.get(Math.max(idx1, idx2));
        lectureBoxControllers.remove(idx1 < idx2 ? idx2 : idx1);
        lectureBoxControllers.add(Math.min(idx1, idx2), removed);
    }

    public void removeLectureBoxController(int idx) {
        lectureBoxControllers.remove(idx);
        for (int i = 0; i < lectureBoxControllers.size(); i++) {
            lectureBoxControllers.get(i).setLectureNo(i + 1);
        }
    }

    public void uploadToDB(Course course) {
        week = new Week(Integer.parseInt(weekNo.getText()), weekNameLabel.getText(), course);
        for (LectureBoxController lecCtrl : lectureBoxControllers) {
            lecCtrl.setWeek(week);
            Lecture lecture = lecCtrl.uploadToDB();
            lectures.add(lecture);
        }
    }
    
    public boolean isPassedCondition(){
        if(weekNameLabel.getText().equals("")){
            ToolKit.showWarning("Week no" + weekNo.getText() + "is empty. \nWeek name can not be empty!");
            return false;
        }
        if(lectureBoxControllers.size()==0){
            ToolKit.showWarning("A week can not be made without lectures");
            return false;
        }
        for(LectureBoxController lecCtrl : lectureBoxControllers){
            if(!lecCtrl.isPassedCondition()) return false;
        }
        return true;
    }
    
    public int getWeekNo(){
        return Integer.parseInt(weekNo.getText());
    }
}
