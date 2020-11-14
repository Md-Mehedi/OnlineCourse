package Course.Overflow.Course.Show;

import Course.Overflow.Course.Contents.ReviewInputBoxController;
import Course.Overflow.Course.Course;
import Course.Overflow.Global.Components.CheckoutPageController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CourseDetailsController implements Initializable {

    @FXML
    private VBox courseContentBox;
    @FXML
    private VBox requirementBox;
    @FXML
    private Label courseDescription;
    @FXML
    private Label instName;
    @FXML
    private Label instShortDes;
    @FXML
    private Label instRating;
    @FXML
    private Label instReviews;
    @FXML
    private Label instStudents;
    @FXML
    private Label instCourses;
    @FXML
    private Label instDes;
    @FXML
    private VBox reviewsBox;
    @FXML
    private VBox courseLearnBox;
    @FXML
    private ImageView instPhoto;
    @FXML
    private Label title;
    @FXML
    private Label subTitle;
    @FXML
    private Label rating;
    @FXML
    private Label numOfStudent;
    @FXML
    private Label topInstName;
    private Label lastUpdate;
    @FXML
    private Label language;
    @FXML
    private Label price;
    @FXML
    private Label mainPrice;
    @FXML
    private Label off;
    @FXML
    private VBox properties;

    private AnchorPane courseContent;
    @FXML
    private Label writeReviewBtn;
    private FXMLLoader loader;
    private AnchorPane reviewInputPane;
    private ReviewInputBoxController reviewInputCtrl;
    private static boolean isReviewInputBoxAdded = false;
    @FXML
    private Label buyNowButton;
    private AnchorPane checkoutPane;
    private CheckoutPageController checkoutCtrl;
    private Course course;
    private CurriculumController curriculumCtrl;
    @FXML
    private ImageView courseimage;
    @FXML
    private Label offer;
    @FXML
    private Label publish;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeCourseWeek();
        makeReviewBox();

        addListener();
    }

    public void makeCourseWeek() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/Curriculum.fxml"));
            courseContent = loader.load();
            curriculumCtrl = loader.<CurriculumController>getController();
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        courseContentBox.getChildren().add(courseContent);
    }

    private void makeReviewBox() {
        for (int i = 0; i < 5; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/Review.fxml"));
            try {
                //AnchorPane pane = new AnchorPane();
                AnchorPane pane = loader.load();
                reviewsBox.getChildren().add(pane);
            } catch (IOException ex) {
                Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            // Review input box added
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CONTENTS_LOCATION + "/ReviewInputBox.fxml"));
            reviewInputPane = loader.load();
            reviewInputCtrl = loader.<ReviewInputBoxController>getController();
            // Checkout page added
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/CheckoutPage.fxml"));
            checkoutPane = loader.load();
            checkoutCtrl = loader.<CheckoutPageController>getController();
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addListener() {
        instName.setOnMouseClicked(event -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.TeacherDetails);
        });
        instPhoto.setOnMouseClicked(event -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.TeacherDetails);
        });
        topInstName.setOnMouseClicked(event -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.TeacherDetails);
        });
        writeReviewBtn.setOnMouseClicked((event) -> {
            reviewInputCtrl.show();
        });
        buyNowButton.setOnMouseClicked((event) -> {
            checkoutCtrl.show();
        });

    }

    private void loadData() {
        title.setText(course.getTitle());
        curriculumCtrl.loadData(course);
        Image img = new Image(course.getCourseImage().getContent());
        courseimage.setImage(img);
        mainPrice.setText(course.getMainPrice().toString());
        offer.setText(course.getOff().toString());
        title.setText(course.getTitle());
        subTitle.setText(course.getSubTitle());
        String name = course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName();
        topInstName.setText(name);
        publish.setText(ToolKit.DateToLocalDate(course.getPublishDate()).toString());
        ArrayList<Language> languag = course.getLanguages();
        Set<String> ln = new HashSet<String>();
        for (Language l : languag) {
            ln.add(l.getName());
        }
        String lang = "";

        for (String l : ln) {
            lang += " , " + l;
        }
        lang = lang.replaceFirst(" ,", "");
        System.out.println(lang);
        this.language.setText(lang);
    }

    public void setCourse(Course course) {
        this.course = course;
        loadData();
    }

}
