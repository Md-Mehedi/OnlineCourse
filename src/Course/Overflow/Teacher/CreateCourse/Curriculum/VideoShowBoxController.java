/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Teacher.CreateCourse.Curriculum.LectureBoxController.LectureType;
import java.io.File;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class VideoShowBoxController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private MediaView mediaView;
    @FXML
    private VBox playBtn;
    @FXML
    private Label description;
    @FXML
    private Button updateBtn;
    private LectureBoxController parent;
    private File file;
    private Media me;
    private MediaPlayer mp;
    private Lecture lecture;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playBtn.setOpacity(0);
        description.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH - mediaView.getFitWidth() - 20);
//        fileNameLabel.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH - mediaView.getFitWidth() - 20);
    }

    void setParent(LectureBoxController parent) {
        this.parent = parent;
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException {
        Object src = event.getSource();
        if (src == updateBtn) {
            parent.setLectureLoaded(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/CourseContentsUploader.fxml"));
            AnchorPane pane = loader.load();
            loader.<CourseContentsUploaderController>getController().setParent(parent, LectureType.VIDEO, false);
            loader.<CourseContentsUploaderController>getController().setFile(file);
            loader.<CourseContentsUploaderController>getController().setDescription(description.getText());
            parent.getAvailableContentContainer().getChildren().remove(container);
            parent.getAvailableContentContainer().getChildren().add(pane);
        } else if (src == playBtn) {
            GLOBAL.PLAYER_CTRL.setFile(file);
            GLOBAL.PLAYER_CTRL.show();
//            me = new Media(file.toURI().toString());
//            mp = new MediaPlayer(me);
//            mediaView.setMediaPlayer(mp);
//            mp.setAutoPlay(true);
//            playBtn.setVisible(false);
        }
    }
    
    public void closeMedieaPlayer(){
        if(mp != null) mp.dispose();
    }

    void setFile(File f) {
        this.file = f;
//        fileNameLabel.setText(file.getName());
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
        if (event.getSource() == playBtn) {
            playBtn.setOpacity(1);
        }
    }

    @FXML
    private void mouseExited(MouseEvent event) {
        if (event.getSource() == playBtn) {
            playBtn.setOpacity(0);
        }
    }

    void setDescription(String description) {
        this.description.setText(description);
    }

    public Files uploadToDB() {
        return new Files(file, FileType.VIDEO, description.getText());
    }
    
    public void updateDB(){
        lecture.getFile().setTitle(description.getText());
        lecture.getFile().setFile(file);
    }

    void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    void stopEditingFunctionality() {
        Pane parent = (Pane) updateBtn.getParent();
        parent.getChildren().remove(updateBtn);
    }
}
