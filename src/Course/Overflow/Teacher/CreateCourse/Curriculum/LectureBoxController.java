/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController.ViewerType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
    private ViewerType viewer;
    @FXML
    private AnchorPane container;
    @FXML
    private HBox lecNoContainer;
    @FXML
    private HBox editDeleteContainer;
    @FXML
    private HBox contentContiner;
    private AnchorPane articleOutputPane;
    private ArticleOutputBoxController articleOutputCtrl;
    private VideoShowBoxController videoShowCtrl;
    private AnchorPane videoShowPane;
    private AnchorPane pdfShowPane;
    private PDFShowBoxController pdfShowCtrl;
    private AnchorPane linkOutputPane;
    private LinkOutputBoxController linkOutputCtrl;

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
            if(lecture!=null) {
                if(fileType == LectureType.VIDEO) videoShowCtrl.closeMedieaPlayer();
                lecture.delete();
            }
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
            if(articleOutputCtrl == null){
                fileType = LectureType.ARTICLE;
                loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/ArticleOutputBox.fxml"));
                articleOutputPane = loader.load();
                articleOutputCtrl = loader.getController();
                articleOutputCtrl.setParent(this);
                this.setCancelVisible(false);
                if(viewer != ViewerType.OwnerTeacherEditor){
                    articleOutputCtrl.stopEditingFunctionality();
                }
                this.content.setVisible(false);
            }
            articleOutputCtrl.setTitle(title);
            articleOutputCtrl.setArticle(article);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(articleOutputPane);
            this.setLectureLoaded(true);
            return articleOutputCtrl;
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
            if(videoShowCtrl == null){
                fileType = LectureType.VIDEO;
                loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/VideoShowBox.fxml"));
                videoShowPane = (AnchorPane) loader.load();
                videoShowCtrl = (VideoShowBoxController)loader.getController();
                videoShowCtrl.setParent(this);
                if(viewer != ViewerType.OwnerTeacherEditor){
                    videoShowCtrl.stopEditingFunctionality();
                }
                this.setCancelVisible(false);
                this.content.setVisible(false);
            }
            videoShowCtrl.setFile(file);
            videoShowCtrl.setDescription(description);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(videoShowPane);
            this.setLectureLoaded(true);
            return videoShowCtrl;
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
            if(pdfShowCtrl == null){
                fileType = LectureType.PDF;
                loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/PDFShowBox.fxml"));
                pdfShowPane = loader.load();
                pdfShowCtrl = loader.getController();
                pdfShowCtrl.setParent(this);
                if(viewer != ViewerType.OwnerTeacherEditor){
                    pdfShowCtrl.stopEditingFunctionality();
                }
                this.setCancelVisible(false);
                this.content.setVisible(false);
            }
            pdfShowCtrl.setFile(file);
            pdfShowCtrl.setDescription(description);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(pdfShowPane);
            this.setLectureLoaded(true);
            return pdfShowCtrl;
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
            if(linkOutputCtrl == null){
                fileType = LectureType.LINK;
                loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/LinkOutputBox.fxml"));
                linkOutputPane = loader.load();
                linkOutputCtrl = loader.getController();
                linkOutputCtrl.setParent(this);
                if(viewer != ViewerType.OwnerTeacherEditor){
                    ToolKit.print(viewer);
                    linkOutputCtrl.stopEditingFunctionality();
                }
                this.setCancelVisible(false);
                this.content.setVisible(false);
            }
            linkOutputCtrl.setLink(link);
            linkOutputCtrl.setLinkDesc(description);
            this.getAvailableContentContainer().getChildren().clear();
            this.getAvailableContentContainer().getChildren().add(linkOutputPane);
            this.setLectureLoaded(true);
            return linkOutputCtrl;
        } catch (IOException ex) {
            Logger.getLogger(LectureBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void addLinkOutputBox(Lecture lecture){
        LinkOutputBoxController ctrl = addLinkOutputBox(lecture.getFile().getContent(), lecture.getFile().getTitle());
        ctrl.setLecture(lecture);
    }

    public void loadData(Week week, Lecture lecture, ViewerType viewer) {
        this.week = week;
        this.lecture = lecture;
        this.viewer = viewer;
        
        lectureNameLabel.setText(lecture.getTitle());
        freeAvailableCkB.setSelected(lecture.isPreview);
        //System.out.println(lecture.getFile().getTypeName().getTypeName());
        if(viewer == ViewerType.OwnerTeacherEditor){
            if(lecture.getFile().getType() == FileType.ARTICLE){
                addArticleOutputBox(lecture);
            }
            else if(lecture.getFile().getType() == FileType.VIDEO){
                addVideoOutputBox(lecture);
            }
            else if(lecture.getFile().getType() == FileType.PDF){
                addPDFOutputBox(lecture);
            }
            else if(lecture.getFile().getType() == FileType.LINK){
                addLinkOutputBox(lecture);
            }
        }
        else{
            stopEditingFunctionality();
            if(viewer == ViewerType.Admin || 
                  viewer == ViewerType.OwnerStudent || 
                  viewer == ViewerType.OwnerTeacherNormal ||
                  (viewer == ViewerType.NormalStudent && lecture.isPreview ||
                  (viewer == ViewerType.NormalTeacher && lecture.isPreview))){
                makeIconToShowCourse();
            }
        }
    }

    private void stopEditingFunctionality() {
        lecNoContainer.getChildren().remove(freeAvailableCkB);
        editDeleteContainer.getChildren().remove(editIcon);
        editDeleteContainer.getChildren().remove(deleteIcon);
        contentContiner.getChildren().clear();
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

    public Lecture uploadToDB(Week week) {
        this.week = week;
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
            Lecture lec = uploadToDB(week);
            week.addLecture(lec);
            return;
        }
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
    
    private void makeIconToShowCourse() {
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHEVRON_DOWN);
        icon.setGlyphSize(25);
        contentContiner.getChildren().add(icon);
        icon.setOnMouseClicked((event) -> {
            if(icon.getRotate() == 0){
                icon.setRotate(180);
                if(lecture.getFile().getType() == FileType.ARTICLE){
                    addArticleOutputBox(lecture);
                }
                else if(lecture.getFile().getType() == FileType.VIDEO){
                    addVideoOutputBox(lecture);
                }
                else if(lecture.getFile().getType() == FileType.PDF){
                    addPDFOutputBox(lecture);
                }
                else if(lecture.getFile().getType() == FileType.LINK){
                    addLinkOutputBox(lecture);
                }
            }
            else{
                icon.setRotate(0);
                availableContentContainer.getChildren().clear();
            }
        });
    }
    void setViewer(ViewerType viewer) {
        this.viewer = viewer;
    }
    
    LectureType getLectureType() {
        return fileType;
    }

    VideoShowBoxController getVideoShowCtrl() {
        return videoShowCtrl;
    }
}
