/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.CourseLandingPage;

import Course.Overflow.Course.Category;
import Course.Overflow.Course.Course;
import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.Customize.HoverEffect;
import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import Course.Overflow.Teacher.CreateCourse.Pricing.PricingController;
import Course.Overflow.Teacher.CreateCourse.TargetStudentPage.TargetStudentPageController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailsController implements Initializable {

//      private ChoiceBox<String> languageCB;
//      private ChoiceBox<String> selectLevelCB;
    @FXML
    private ChoiceBox<String> mainCategoryCB;
    @FXML
    private ChoiceBox<String> subCategoryCB;
    @FXML
    private AnchorPane container;
    @FXML
    private TextField courseTitle;
    @FXML
    private TextField courseSubTitle;
    @FXML
    private TextArea courseDescription;
    @FXML
    private TextField primTaught;
    @FXML
    private ImageView courseImage;
    @FXML
    private Label courseImageLabel;
    @FXML
    private Button courseImageUpBtn;
    private ImageView courseVideoImage;
    private MediaView courseVideoMV;
    private Label courseVideoLabel;
    private Button courseVideoUpBtn;
    private HBox langContainer;
    private ImageView add;
    private ObservableList langList;
    private ImageView addLangIcon;
    private FontAwesomeIconView taughtInfoIcon;
    @FXML
    private FontAwesomeIconView imageInfoIcon;
//      private FontAwesomeIconView videoInfoIcon;
    @FXML
    private Label ct;
    private AnchorPane parentPane;
    private File photoFile;
    @FXML
    private TextField languageField;
    private HashMap<Integer, CheckBox> checkBoxes;
    private ArrayList<Language> selectedLanguage;
    private PricingController pricingCtrl;
    private CurriculumController curriculumCtrl;
    private TargetStudentPageController targetStudentCtrl;
    private boolean newCourse;
    private Course course;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newCourse = true;
        readyLanguageList();
        readyLevel();
        readyCategory();
        readySubCategory();
        new ToolTip(MouseEvent.MOUSE_ENTERED, imageInfoIcon, "Make your course stand out with a great image. The "
              + "image must maintain 16:9 ratio and .jpg, .jpeg, .gif or .png format");
//            new ToolTip(MouseEvent.MOUSE_ENTERED, videoInfoIcon, "Make your course attractive with a promo video. "
//                  + "Students who watch a well-made promo video are 5x more likely to erroll "
//                  + "in your course.");
//        new ToolTip(MouseEvent.MOUSE_ENTERED, addLangIcon, "Add secondary language. If you have subtitles of "
//              + "different language in your course video.");
//        new ToolTip(MouseEvent.MOUSE_ENTERED, taughtInfoIcon, "Each individual topic chosen should comprehensively"
//              + " describe your course's content without being too broad. E.g. \"The Complete"
//              + " Tennis Course\" should have \"Tennis\" – not \"Tennis Serve\" (specific, but"
//              + " not comprehensive) and not \"Sports\" (comprehensive, but not specific).");
    }

    @SuppressWarnings("unchecked")
    private void readyLanguageList() {

        checkBoxes = new HashMap<>();
        selectedLanguage = new ArrayList<>();

        ArrayList<Language> list = Language.getList();
        VBox container = new VBox();
        AnchorPane root = new AnchorPane(container);
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");

        for (Language l : list) {
            CheckBox cb = new CheckBox(l.getName());
            checkBoxes.put(l.getId(), cb);
            //System.out.println(checkBoxes.get(l.getId()));
            //checkBoxes.get(l.getId()).setSelected(true);

            container.getChildren().add(cb);
            cb.setStyle(cb.getStyle() + "-fx-font-size: 18;");
            cb.setOnMouseClicked((event) -> {
                if (cb.isSelected()) {
                    selectedLanguage.add(l);
                } else {
                    selectedLanguage.remove(l);
                }
                refreshLanguageString();
            });
        }

        new HoverEffect(languageField, root) {
            @Override
            public void setLocation() {
                this.defaultLocation();
            }
        };

        Platform.runLater(() -> {
            root.setStyle(
                  root.getStyle()
                  + "-fx-background-color: white;"
                  + "-fx-pref-width: 350;"
            );
            System.out.println(languageField.getPrefWidth() + 100);
            container.setStyle(
                  container.getStyle()
                  + "-fx-alignment: center-left;"
                  + "-fx-spacing: 5; "
            );
        });

    }

    private void refreshLanguageString() {
        String text = "";
        String[] languages = new String[selectedLanguage.size()];

        for (int i = 0; i < selectedLanguage.size(); i++) {
            languages[i] = selectedLanguage.get(i).getName();
        }
        Arrays.sort(languages);
        for (String name : languages) {
            if (text == "") {
                text = name;
            } else {
                text += ", " + name;
            }
        }
        languageField.setText(text);
    }
    
    private void updateLanguage(ArrayList<Language> languages) {
        for(Language lang : languages){
            selectedLanguage.add(lang);
            CheckBox cb = checkBoxes.get(lang.getId());
            cb.setSelected(true);
            cb.setOnMouseClicked((event) -> {
                if (cb.isSelected()) {
                    selectedLanguage.add(lang);
                } else {
                    selectedLanguage.remove(lang);
                }
                refreshLanguageString();
            });
        }
        refreshLanguageString();
    }

    @SuppressWarnings("unchecked")
    private void readyLevel() {
//        ObservableList list = FXCollections.observableArrayList();
//        list.add("--Select Level--");
//        list.addAll("Beginner", "Medium", "Pro");
//        selectLevelCB.setItems(list);
//        selectLevelCB.setValue("--Select Level--");

    }

    @SuppressWarnings("unchecked")
    private void readyCategory() {
        ObservableList list = FXCollections.observableArrayList();
        list.add("--Select Main Category--");
        for(Category category : Category.getMainCategories()){
            list.add(category.getName());
        }
        mainCategoryCB.setItems(list);
        mainCategoryCB.setValue("--Select Main Category--");
        mainCategoryCB.setOnAction((event) -> {
            subCategoryCB.getItems().clear();
            ObservableList subCatList = FXCollections.observableArrayList();
            subCategoryCB.setItems(subCatList);
            subCatList.add("--Select Sub Category--");
            subCategoryCB.setValue("--Select Sub Category--");
            if(mainCategoryCB.getValue().equals("--Select Main Category--")) return;
            for(Category category : Category.getSubCategories(new Category(mainCategoryCB.getValue()))){
                subCatList.add(category.getName());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void readySubCategory() {
        ObservableList list = FXCollections.observableArrayList();
        list.add("--Select Sub Category--");
        subCategoryCB.setItems(list);
        subCategoryCB.setValue("--Select Sub Category--");
    }

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
        Object src = event.getSource();
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws FileNotFoundException {
        Object src = event.getSource();

        if (src == courseImageUpBtn) {
            File tempFile = ToolKit.chooseFile(FileType.PICTURE);
            if(tempFile != null) photoFile = tempFile;
            if (photoFile != null) {
                courseImage.setImage(new Image(new FileInputStream(photoFile)));
                courseImageLabel.setText(photoFile.getName());
            }
        } else if (src == courseVideoUpBtn) {
            File file = ToolKit.chooseFile(FileType.VIDEO);
            if (file != null) {
                courseVideoImage.visibleProperty().set(false);
                courseVideoLabel.setText(file.getName());
                courseVideoMV.setMediaPlayer(ToolKit.getMediaPlayer(file));
            }
        } else if (src == addLangIcon) {
            ChoiceBox<String> newLang = new ChoiceBox<>(langList);
            langContainer.getChildren().add(langContainer.getChildren().size() - 1, newLang);
            newLang.setValue((String) langList.get(0));
            newLang.getStyleClass().add("choiceBox");
            if (langContainer.getChildren().size() == 4) {
                langContainer.getChildren().remove(addLangIcon);
            }
        }
    }
    
    public void updateDB(){
        if(!isPassedCondition()) return;
        course.setTitle(courseTitle.getText());
        course.setSubTitle(courseSubTitle.getText());
        course.setDescription(courseDescription.getText());
        course.getCourseImage().setFile(photoFile);
        course.setLanguages(selectedLanguage);
        course.setSubCategory(new Category(subCategoryCB.getValue()));
        course.setMainPrice(pricingCtrl.getPrice());
        course.setOff(pricingCtrl.getOffer());
    }

    public Course uploadToDB() {
        Files coursePhoto = new Files(photoFile, FileType.PICTURE, "Course Cover");
        Category subCat = new Category(subCategoryCB.getValue());
        course = new Course(courseTitle.getText(), courseSubTitle.getText(), courseDescription.getText(), pricingCtrl.getPrice(), coursePhoto, subCat);
        course.setOff(pricingCtrl.getOffer());
        course.setLanguages(selectedLanguage);
        return course;
    }

    public void setControllers(TargetStudentPageController targetStudentCtrl, CurriculumController curriculumCtrl, PricingController pricingController) {
        this.targetStudentCtrl = targetStudentCtrl;
        this.curriculumCtrl = curriculumCtrl;
        this.pricingCtrl = pricingController;
    }

    public Boolean isPassedCondition() {
        if(courseTitle.getText().equals("")){
            ToolKit.showWarning("Please set a title for your course.");
            return false;
        }
        if(courseSubTitle.getText().equals("")){
            ToolKit.showWarning("Please set a subtitle for your course.");
            return false;
        }
        if(!(5 < courseDescription.getText().length() && courseDescription.getText().length() < 1000)){
            ToolKit.showWarning("Please write a description between 50 to 1000 charecter.");
            return false;
        }
        if(photoFile==null){
            ToolKit.showWarning("Please set a attractive photo for your course.");
            return false;
        }
        if(selectedLanguage.size()==0){
            ToolKit.showWarning("Please set atleast a language for your course.");
            return false;
        }
        if(subCategoryCB.getValue().equals("--Select Sub Category--")){
            ToolKit.showWarning("Please set a category and sub-category for your course.");
            return false;
        }
        return true;
    }
    
    public void loadData(Course course){
        this.course = course;
        courseTitle.setText(course.getTitle());
        courseSubTitle.setText(course.getSubTitle());
        courseDescription.setText(course.getDescription());
        photoFile = new File(ToolKit.makeAbsoluteLocation(course.getCourseImage().getContent()));
        courseImage.setImage(new Image(photoFile.toURI().toString()));
        mainCategoryCB.setValue(course.getMainCategory().getName());
        subCategoryCB.setValue(course.getSubCategory().getName());
        updateLanguage(course.getLanguages());
    }

    public void createEnvironmentForCourseUpdate(Course course){
        this.course = course;
        newCourse = false;
    }
    
}
