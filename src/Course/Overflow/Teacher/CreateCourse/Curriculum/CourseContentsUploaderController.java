/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.Curriculum.ContentsListBoxController.LectureType;
import com.qoppa.pdf.PDFException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CourseContentsUploaderController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private Label fileNameLabel;
    @FXML
    private Button chooseFileBtn;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button cancelBtn;

    private LectureBoxController parent;
    private LectureType type;
    private File file;
    private boolean isNew;
    private File oldFile;
    @FXML
    private TextArea description;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Tools.makeDynamicTextArea(description);
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException, PDFException {
        Object src = event.getSource();
        if (src == chooseFileBtn) {
            FileChooser fc = new FileChooser();
            if (type == LectureType.VIDEO) {
                fc.getExtensionFilters().addAll(
                      new FileChooser.ExtensionFilter("MP4 Files", "*.mp4"),
                      new FileChooser.ExtensionFilter("3gp Files", "*.3gp")
                );
            } else {
                fc.getExtensionFilters().add(
                      new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
                );
            }
            file = fc.showOpenDialog(null);
            if (file != null) {
                fileNameLabel.setText(file.getName());
            } else {
                fileNameLabel.setText("");
            }
        } else if (src == uploadBtn || (src == cancelBtn && isNew == false)) {
            if(!isPassedCondition()) return;
            AnchorPane pane = new AnchorPane();
            if (isNew == false && src != uploadBtn) {
                file = oldFile;
            }
            if (type == LectureType.VIDEO) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/VideoShowBox.fxml"));
                pane = loader.load();
                VideoShowBoxController ctrl = loader.getController();
                ctrl.setParent(parent);
                ctrl.setFile(file);
                ctrl.setDescription(description.getText());
            } else if (type == LectureType.PDF) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/PDFShowBox.fxml"));
                pane = loader.load();
                PDFShowBoxController ctrl = loader.getController();
                ctrl.setParent(parent);
                ctrl.setFile(file);
                ctrl.setDescription(description.getText());
            }
            parent.getAvailableContentContainer().getChildren().add(pane);
            parent.getAvailableContentContainer().getChildren().remove(container);
            parent.setCancelVisible(false);
            parent.setLectureLoaded(true);
        } else if (src == cancelBtn) {
            parent.getAvailableContentContainer().getChildren().remove(container);
            parent.setCancelVisible(false);
            parent.setContentVisible(true);
        }
    }

    public void setParent(LectureBoxController parent, LectureType type, boolean isNew) {
        this.parent = parent;
        this.type = type;
        this.isNew = isNew;
    }

    public void setFile(File file) {
        oldFile = file;
        this.file = file;
        fileNameLabel.setText(file.getName());
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public Files uploadToDB() {
        if (type == LectureType.VIDEO) {
            return new Files(file, FileType.toType("Video"), description.getText());
        }
        if (type == LectureType.PDF) {
            return new Files(file, FileType.toType("PDF"), description.getText());
        }
        return null;
    }

    public boolean isPassedCondition() {
        if(fileNameLabel.getText().equals("")){
            ToolKit.showWarning("You must select a file.");
            return false;
        }
        if(!(5 < description.getText().length() && description.getText().length() <= 1000)){
            ToolKit.showWarning("File description have to be between 50 to 1000 charecters");
            return false;
        }
        return true;
    }

}
