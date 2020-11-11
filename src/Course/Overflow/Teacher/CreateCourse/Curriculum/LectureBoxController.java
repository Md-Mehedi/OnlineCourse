/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Files.Files;
import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private boolean isLectureLoaded;

    public enum LectureType {
        ARTICLE,
        VIDEO,
        LINK,
        PDF
    }

    private LectureType fileType;
    private FXMLLoader loader;

    public boolean isIsLectureLoaded() {
        return isLectureLoaded;
    }

    public void setLectureLoaded(boolean isLectureLoaded) {
        this.isLectureLoaded = isLectureLoaded;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isLectureLoaded = false;
        lectureNameLabel.setMaxWidth(GLOBAL.LABEL_PREF_WIDTH - 420);
        availableContentContainer.setPadding(new Insets(0, GLOBAL.AVAILABLE_CONTENT_CON_PADDING, 0, GLOBAL.AVAILABLE_CONTENT_CON_PADDING));
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
        hbox.setPadding(new Insets(0, 0, 0, 10));
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

//    private void upClicked(MouseEvent event) {
//        VBox parent = (VBox) lectureBoxPane.getParent();
//        int idx = parent.getChildren().indexOf(lectureBoxPane);
//        if (idx == 0) {
//            return;
//        }
//        AnchorPane upperBox = (AnchorPane) parent.getChildren().get(idx - 1);
//        AnchorPane bottomBox = (AnchorPane) parent.getChildren().get(idx);
//        parent.getChildren().removeAll(upperBox, bottomBox);
//        parent.getChildren().add(idx - 1, bottomBox);
//        parent.getChildren().add(idx, upperBox);
//    }
//
//    private void downClicked(MouseEvent event) {
//        VBox parent = (VBox) lectureBoxPane.getParent();
//        int idx = parent.getChildren().indexOf(lectureBoxPane);
//        if (idx == parent.getChildren().size() - 1) {
//            return;
//        }
//        AnchorPane upperBox = (AnchorPane) parent.getChildren().get(idx);
//        AnchorPane bottomBox = (AnchorPane) parent.getChildren().get(idx + 1);
//        parent.getChildren().removeAll(upperBox, bottomBox);
//        parent.getChildren().add(idx, bottomBox);
//        parent.getChildren().add(idx + 1, upperBox);
//    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException {
        Object src = event.getSource();
        if (src == editIcon) {
            HBox parent = (HBox) lectureNameLabel.getParent();
            editIcon.setVisible(false);
            deleteIcon.setVisible(false);
            parent.getChildren().remove(lectureNameLabel);

            TextField field = new TextField(lectureNameLabel.getText());
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
                            lectureNameLabel.setText(field.getText());
                        }
                        editIcon.visibleProperty().set(true);
                        deleteIcon.visibleProperty().set(true);
                        parent.getChildren().add(lectureNameLabel);
                    }
                });
            }
        } else if (src == up || src == down) {
            VBox parent = (VBox) lectureBoxPane.getParent();
            int idx = parent.getChildren().indexOf(lectureBoxPane);
            ToolKit.moveRow(parent, idx, src == up ? -1 : 1);
            ToolKit.moveRow(parentController.getLectureBoxControllers(), idx, src == up ? -1 : 1);
            parentController.refreshLectureNo();
        } else if (src == deleteIcon) {
            VBox parent = (VBox) lectureBoxPane.getParent();
            parent.getChildren().remove(lectureBoxPane);
            parentController.getLectureBoxControllers().remove(this);
            if(lecture!=null) lecture.delete();
        } else if (src == content) {
            content.setVisible(false);
            cancelContents.setVisible(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ContentsListBox.fxml"));
            AnchorPane pane;
            pane = (AnchorPane) loader.load();
            contentListCtrl = loader.<ContentsListBoxController>getController();
            contentListCtrl.setParent(this);
            availableContentContainer.getChildren().add(pane);
        } else if (src == cancelContents) {
            content.setVisible(true);
            cancelContents.setVisible(false);
            availableContentContainer.getChildren().clear();
        }
    }

    public AnchorPane addArticleInputBox() {
        AnchorPane pane = null;
        try {
            fileType = LectureType.ARTICLE;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ArticleInputBox.fxml"));
            pane = loader.load();
            loader.<ArticleInputBoxController>getController().setParent(this, true);
            //if(lecture != null) loader.<ArticleInputBoxController>getController().loadData(lecture);
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }

    public ArticleOutputBoxController addArticleOutputBox(String title, String article) {
        try {
            fileType = LectureType.ARTICLE;
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ArticleOutputBox.fxml"));
            AnchorPane pane = loader.load();
            ArticleOutputBoxController ctrl = loader.getController();
            ctrl.setParent(this);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(pane);
            this.setCancelVisible(false);
            ctrl.setTitle(title);
            ctrl.setArticle(article);
            this.setLectureLoaded(true);
            this.content.setVisible(false);
            return ctrl;
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addArticleOutputBox(Lecture lecture) {
        ArticleOutputBoxController ctrl = addArticleOutputBox(lecture.getFile().getTitle(), lecture.getFile().getContent());
        ctrl.setLecture(lecture);
    }

    public AnchorPane addVideoInputBox() {
        AnchorPane pane = null;
        try {
            fileType = LectureType.VIDEO;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/CourseContentsUploader.fxml"));
            pane = loader.load();
            loader.<CourseContentsUploaderController>getController().setParent(this, fileType, true);
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }

    public VideoShowBoxController addVideoOutputBox(File file, String description) {
        try {
            fileType = LectureType.VIDEO;
            AnchorPane pane = new AnchorPane();
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/VideoShowBox.fxml"));
            pane = loader.load();
            VideoShowBoxController ctrl = loader.getController();
            ctrl.setParent(this);
            ctrl.setFile(file);
            ctrl.setDescription(description);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(pane);
            this.setCancelVisible(false);
            this.setLectureLoaded(true);
            this.content.setVisible(false);
            return ctrl;
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void addVideoOutputBox(Lecture lecture){
        VideoShowBoxController ctrl = addVideoOutputBox(new File(ToolKit.makeAbsoluteLocation(lecture.getFile().getContent())), lecture.getFile().getTitle());
        ctrl.setLecture(lecture);
    }

    public AnchorPane addPDFInputBox() {
        AnchorPane pane = null;
        try {
            fileType = LectureType.PDF;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/CourseContentsUploader.fxml"));
            pane = loader.load();
            loader.<CourseContentsUploaderController>getController().setParent(this, fileType, true);
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }

    public PDFShowBoxController addPDFOutputBox(File file, String description) {
        try {
            fileType = LectureType.PDF;
            AnchorPane pane = new AnchorPane();
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/PDFShowBox.fxml"));
            pane = loader.load();
            PDFShowBoxController ctrl = loader.getController();
            ctrl.setParent(this);
            ctrl.setFile(file);
            ctrl.setDescription(description);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(pane);
            this.setCancelVisible(false);
            this.setLectureLoaded(true);
            this.content.setVisible(false);
            return ctrl;
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void addPDFOutputBox(Lecture lecture){
        PDFShowBoxController ctrl = addPDFOutputBox(new File(ToolKit.makeAbsoluteLocation(lecture.getFile().getContent())), lecture.getFile().getTitle());
        ctrl.setLecture(lecture);
    }

    public AnchorPane addLinkInputBox() {
        AnchorPane pane = null;
        try {
            fileType = LectureType.LINK;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/LinkInputBox.fxml"));
            pane = loader.load();
            loader.<LinkInputBoxController>getController().setParent(this, true);
            //if(lecture != null) loader.<LinkInputBoxController>getController().loadData(lecture);
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }
    
    public LinkOutputBoxController addLinkOutputBox(String link, String description){
        try {
            fileType = LectureType.LINK;
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/LinkOutputBox.fxml"));
            AnchorPane pane = loader.load();
            LinkOutputBoxController ctrl = loader.getController();
            ctrl.setParent(this);
            ctrl.setLink(link);
            ctrl.setLinkDesc(description);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(pane);
            this.setCancelVisible(false);
            this.setLectureLoaded(true);
            this.content.setVisible(false);
            return ctrl;
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void addLinkOutputBox(Lecture lecture){
        LinkOutputBoxController ctrl = addLinkOutputBox(lecture.getFile().getContent(), lecture.getFile().getTitle());
        ctrl.setLecture(lecture);
    }

    public void loadData(Lecture lecture) {
        this.lecture = lecture;
        lectureNameLabel.setText(lecture.getTitle());
        freeAvailableCkB.setSelected(lecture.isPreview);
        //System.out.println(lecture.getFile().getType().getType());
        if(lecture.getFile().getType().getType().equals("Article")){
            addArticleOutputBox(lecture);
        }
        else if(lecture.getFile().getType().getType().equals("Video")){
            addVideoOutputBox(lecture);
        }
        else if(lecture.getFile().getType().getType().equals("PDF")){
            addPDFOutputBox(lecture);
        }
        else if(lecture.getFile().getType().getType().equals("Link")){
            addLinkOutputBox(lecture);
        }
    }

    public void setContentVisible(boolean b) {
        content.setVisible(b);
    }

    public void setCancelVisible(boolean b) {
        cancelContents.setVisible(b);
    }

    public HBox getAvailableContentContainer() {
        return availableContentContainer;
    }

    public void setLectureNo(int x) {
        lectureNo.setText("" + x);
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
        System.out.println("uploadDB" + lectureNameLabel.getText());
        Files file = null;
        switch (fileType) {
            case ARTICLE:
                file = loader.<ArticleOutputBoxController>getController().uploadToDB(); break;
            case VIDEO:
                file = loader.<VideoShowBoxController>getController().uploadToDB();     break;
            case PDF:
                file = loader.<PDFShowBoxController>getController().uploadToDB();       break;
            case LINK:
                file = loader.<LinkOutputBoxController>getController().uploadToDB();    break;
        }
        lecture = new Lecture(Integer.parseInt(lectureNo.getText()), lectureNameLabel.getText(), file, freeAvailableCkB.isSelected(), week);
        return lecture;
    }
    
    public void updateDB(){
        if(lecture == null){
            Lecture lec = uploadToDB();
            week.addLecture(lec);
            return;
        }
        System.out.println("updateDB" + lectureNameLabel.getText());
        lecture.setLectureNo(Integer.parseInt(lectureNo.getText()));
        lecture.setTitle(lectureNameLabel.getText());
        lecture.setIsPreview(freeAvailableCkB.isSelected());
        switch (fileType) {
            case ARTICLE:
                loader.<ArticleOutputBoxController>getController().updateDB(); break;
            case VIDEO:
                loader.<VideoShowBoxController>getController().updateDB();     break;
            case PDF:
                loader.<PDFShowBoxController>getController().updateDB();       break;
            case LINK:
                loader.<LinkOutputBoxController>getController().updateDB();    break;
        }
    }

    public boolean isPassedCondition() {
        if (lectureNameLabel.getText().equals("")) {
            ToolKit.showWarning("Lecture name of Week no: " + parentController.getWeekNo() + ", Lecture no: " + lectureNo.getText() + " is empty.\n" + "Lecture name can't be empty.");
            return false;
        }
        if (!isLectureLoaded) {
            ToolKit.showWarning("Lecture field of Week no: " + parentController.getWeekNo() + ", Lecture no: " + lectureNo.getText() + " is not set. \nPlease set a lecture");
            return false;
        }
        return true;
    }
}
