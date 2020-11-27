package Course.Overflow.Course.Show;

import Course.Overflow.Admin.CourseListController;
import Course.Overflow.Course.Contents.ReviewInputBoxController;
import Course.Overflow.Course.Course;
import Course.Overflow.Course.CourseRating;
import Course.Overflow.Course.FAQ;
import Course.Overflow.Course.Property;
import Course.Overflow.Course.Review;
import Course.Overflow.Global.Components.CheckoutPageController;
import Course.Overflow.Global.Customize.Icon;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.PersonPreviewController;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.CreateCourse;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController.ViewerType;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import org.controlsfx.control.Rating;

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
    private VBox reviewContainer;
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
    private VBox properties;

    private AnchorPane courseContent;
    @FXML
    private Label writeReviewBtn;
    private FXMLLoader loader;
    private AnchorPane reviewInputPane;
    private ReviewInputBoxController reviewInputCtrl;
    private static boolean isReviewInputBoxAdded = false;
    @FXML
    private JFXButton buyNowButton;
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
    private ReviewController reviewBoxCtrl;
    @FXML
    private Rating ratingStar;
    @FXML
    private AnchorPane root;
    @FXML
    private Label ratingCount1;
    @FXML
    private Label ratingCount2;
    @FXML
    private Label ratingCount3;
    @FXML
    private Label ratingCount4;
    @FXML
    private Label ratingCount5;
    @FXML
    private Rating studentRating;
    @FXML
    private JFXButton ratingSubmit;
    @FXML
    private Rating rating1;
    @FXML
    private Rating rating2;
    @FXML
    private Rating rating3;
    @FXML
    private Rating rating4;
    @FXML
    private Rating rating5;
    private CourseRating submittedRating;
    @FXML
    private Label giveRatingLabel;
    @FXML
    private Label curRatingValue;
    @FXML
    private Rating curRating;
    @FXML
    private VBox buyNowContainer;
    @FXML
    private HBox priceContainer;
    private ViewerType viewer;
    @FXML
    private VBox studentRatingContainer;
    @FXML
    private HBox buyNowBtnContainer;
    private CourseListController adminCtrl;
    @FXML
    private Label askQuestionBtn;
    @FXML
    private VBox faqContainer;
    private AnchorPane questionInputPane;
    private FAQInputBoxController questionInputCtrl;
    private FAQOutputBoxController faqOutputBoxCtrl;
    @FXML
    private VBox reviewRootContainer;
    @FXML
    private VBox faqRootContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fixupRatings();
        connectCurriculum();
        connectReviewInputBox();
        connectQuestionInputBox();
        connectCheckoutBox();

        addListener();
    }

    private void connectCurriculum() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/Curriculum.fxml"));
            courseContent = loader.load();
            curriculumCtrl = loader.<CurriculumController>getController();
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        courseContentBox.getChildren().add(courseContent);
    }

    private void connectReviewInputBox() {
        try {
            // Review input box added
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CONTENTS_LOCATION + "/ReviewInputBox.fxml"));
            reviewInputPane = loader.load();
            reviewInputCtrl = loader.<ReviewInputBoxController>getController();
            reviewInputCtrl.setParent(this);
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectQuestionInputBox() {
        try {
            // FAQ input box added
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/FAQInputBox.fxml"));
            questionInputPane = (AnchorPane) loader.load();
            questionInputCtrl = loader.<FAQInputBoxController>getController();
            questionInputCtrl.setParent(this);
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void connectCheckoutBox(){
        try {
            // Checkout page added
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/CheckoutPage.fxml"));
            checkoutPane = loader.load();
            checkoutCtrl = loader.<CheckoutPageController>getController();
            checkoutCtrl.setParent(this);
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void loadTeacherPage(){
        GLOBAL.PAGE_CTRL.loadPage(PageName.PersonDetails);
        PersonPreviewController ctrl = (PersonPreviewController) GLOBAL.PAGE_CTRL.getController();
        ctrl.loadData(course.getTeacher());
    }

    private void addListener() {
        instName.setOnMouseClicked(event -> {
            loadTeacherPage();
        });
        instPhoto.setOnMouseClicked(event -> {
            loadTeacherPage();
        });
        topInstName.setOnMouseClicked(event -> {
            loadTeacherPage();
        });
        writeReviewBtn.setOnMouseClicked((event) -> {
            reviewInputCtrl.show();
        });
        askQuestionBtn.setOnMouseClicked(event -> {
            questionInputCtrl.show();
        });
        buyNowButton.setOnMouseClicked((event) -> {
            checkoutCtrl.show();
        });
        ratingSubmit.setOnMouseClicked((event) -> {
            submittedRating = new CourseRating(course, GLOBAL.STUDENT, studentRating.getRating());
            refreshData();
            clearRatingSubmitBtn();
            reviewInputCtrl.setRating(submittedRating);
        });
    }
    
    private HBox makeLabelWithIcon(String text, String iconName){
        Label label = new Label(text);
        Icon icon = new Icon(FontAwesomeIcon.valueOf(iconName));
        icon.setSize(25);
        HBox box = new HBox(icon, label);
        box.setSpacing(5);
        return box;
    }

    public void loadData(Course course) {
        this.course = course;
        course.loadAllData();
        
        defineViewerType();
        if(viewer == ViewerType.OwnerTeacherNormal){
            makeBuyNowToUpdateCourse();
        }
        else if(viewer == ViewerType.Admin){
            makeBuyNowToApproveCourse();
        }
        else if(viewer == ViewerType.OwnerStudent || viewer == ViewerType.NormalTeacher){
            removeBuyNowBtn();
        }
        if(viewer == ViewerType.NormalStudent){
            checkoutCtrl.loadData(course);
        }
        if(viewer == ViewerType.NormalStudent || viewer == ViewerType.OwnerStudent){
            reviewInputCtrl.setCourse(course);
            questionInputCtrl.setCourse(course);
        }
        
        title.setText(course.getTitle());
        subTitle.setText(course.getSubTitle());
        topInstName.setText(course.getTeacher().getFullName());
        publish.setText(ToolKit.makeDateStructured(course.getPublishDate(), "dd MMMMM, yyyy"));
        
        ArrayList<Language> language = course.getLanguages();
        Set<String> ln = new HashSet<String>();
        for (Language l : language) {
            ln.add(l.getName());
        }
        String lang = "";
        for (String l : ln) {
            lang += " , " + l;
        }
        lang = lang.replaceFirst(" ,", "");
        this.language.setText(lang);
        
        mainPrice.setText(course.getMainPrice().toString());
        offer.setText(ToolKit.DoubleToString(course.getOff()) + " % off");
        price.setText(course.getCurrentPrice().toString());
        for(Property property : course.getProperties()){
            properties.getChildren().add(makeLabelWithIcon(property.getText(), property.getIcon().getContent()));
        }
        for(String outcome : course.getOutcomes()){
            courseLearnBox.getChildren().add(makeLabelWithIcon(outcome, "CHECK"));
        }
        for(String prerequ : course.getPrerequisitive()){
            requirementBox.getChildren().add(makeLabelWithIcon(prerequ, "CHEVRON_CIRCLE_RIGHT"));
        }
        courseDescription.setText(course.getDescription());
        
        refreshCurriculum();
        
        instName.setText(course.getTeacher().getFullName());
        instPhoto.setImage(course.getTeacher().getImage());
        instDes.setText(course.getTeacher().getAbout());
        
        addRating();
        addReview();
        addFAQ();
        refreshData();
        courseimage.setImage(ToolKit.makeImage(course.getCourseImage()));
    }
    
    public void refreshCurriculum(){
        curriculumCtrl.loadData(course, viewer);
    }
    
    public void refreshData(){
        ratingStar.setRating(course.getRating());
        rating.setText("(" + course.getNumOfRating() +" ratings)");
        numOfStudent.setText(course.getTeacher().getNumOfStudent() + " students");
        
        instRating.setText(ToolKit.DoubleToString(course.getTeacher().getRating()));
        instReviews.setText(course.getTeacher().getNumOfReview().toString() + " reviews");
        instStudents.setText(course.getTeacher().getNumOfStudent().toString() + " students");
        instCourses.setText(course.getTeacher().getNumOfCourse().toString() + " courses");
        
        ratingCount1.setText(CourseRating.getCount(course, 1).toString() + (CourseRating.getCount(course, 1) > 1 ? " students" : " student"));
        ratingCount2.setText(CourseRating.getCount(course, 2).toString() + (CourseRating.getCount(course, 2) > 1 ? " students" : " student"));
        ratingCount3.setText(CourseRating.getCount(course, 3).toString() + (CourseRating.getCount(course, 3) > 1 ? " students" : " student"));
        ratingCount4.setText(CourseRating.getCount(course, 4).toString() + (CourseRating.getCount(course, 4) > 1 ? " students" : " student"));
        ratingCount5.setText(CourseRating.getCount(course, 5).toString() + (CourseRating.getCount(course, 5) > 1 ? " students" : " student"));
        curRating.setRating(course.getRating());
        curRatingValue.setText("(" + ToolKit.DoubleToString(course.getRating()) + ")");
    }

    public void addReviewBox(Review review) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/Review.fxml"));
        try {
            AnchorPane pane = loader.load();
            reviewBoxCtrl = loader.<ReviewController>getController();
            if(review != null) reviewBoxCtrl.loadData(review);
            reviewContainer.getChildren().add(0, pane);
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fixupRatings() {
        ratingStar.setOnMouseClicked((event) -> {
            ratingStar.setRating(course.getRating());
        });
        rating1.setOnMouseClicked((event) -> {
            rating1.setRating(1);
        });
        rating2.setOnMouseClicked((event) -> {
            rating2.setRating(2);
        });
        rating3.setOnMouseClicked((event) -> {
            rating3.setRating(3);
        });
        rating4.setOnMouseClicked((event) -> {
            rating4.setRating(4);
        });
        rating5.setOnMouseClicked((event) -> {
            rating5.setRating(5);
        });
        studentRating.setOnMouseClicked((event) -> {
            if(submittedRating.getValue() != null){
                studentRating.setRating(submittedRating.getValue());
            }
        });
        curRating.setOnMouseClicked((event) -> {
            curRating.setRating(course.getRating());
        });
        
    }

    private void clearRatingSubmitBtn() {
        if(ratingSubmit == null) return;
        VBox box = (VBox) ratingSubmit.getParent();
        box.getChildren().remove(ratingSubmit);
        giveRatingLabel.setText("Your rating");
        ratingSubmit = null;
    }

    public void removeAddReviewBtn() {
        VBox parent = (VBox) writeReviewBtn.getParent();
        int idx = parent.getChildren().indexOf(writeReviewBtn);
        parent.getChildren().remove(writeReviewBtn);
        parent.getChildren().add(idx, new Label("You have already reviewed this course."));
    }

    public void addRating() {
        if(viewer == ViewerType.NormalStudent || viewer == ViewerType.OwnerStudent){
            Integer ratingValue = course.getRatingOf(GLOBAL.STUDENT);
            submittedRating = new CourseRating(course.getId(), GLOBAL.STUDENT);
            if(ratingValue != 0){
                reviewInputCtrl.setRating(submittedRating);
            };
            if(submittedRating.getValue() != null){
                studentRating.setRating(submittedRating.getValue());
                clearRatingSubmitBtn();
            }
        }
        else{
            Pane pane = (Pane) studentRatingContainer.getParent();
            pane.getChildren().remove(studentRatingContainer);
        }
    }
    
    public void removeBuyNowBtn(){
        Pane pane = (Pane) buyNowContainer.getParent();
        pane.getChildren().remove(buyNowContainer);
    }
    
    public void makeBuyNowToUpdateCourse(){
//        Pane pane = (Pane) priceContainer.getParent();
//        pane.getChildren().remove(priceContainer);
        buyNowButton.setText("Update");
        JFXButton deleteBtn = new JFXButton("Delete");
        deleteBtn.getStyleClass().addAll("title1", "myButton");
        deleteBtn.setStyle("-fx-background-color: red;");
        Region region = new Region();
        region.setMinWidth(15);
        HBox.setHgrow(region, Priority.ALWAYS);
        buyNowBtnContainer.getChildren().addAll(region, deleteBtn);
        buyNowButton.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.CreateCourse);
            CreateCourse cc = (CreateCourse) GLOBAL.PAGE_CTRL.getPage();
            cc.loadData(course);
        });
        deleteBtn.setOnMouseClicked((event) -> {
            int state1 = JOptionPane.showConfirmDialog(null, "Do you want to delete your course?\nIf you delete this course, you will never recover it.", "Warning!!!", JOptionPane.CANCEL_OPTION);
            if (state1 == 0) {
                course.delete();
                GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
            }
        });
        Platform.runLater(()->{
            buyNowButton.setPrefWidth(buyNowButton.getPrefWidth()/2-15);
            deleteBtn.setPrefWidth(buyNowButton.getPrefWidth());
        });
    }

    private void defineViewerType() {
        if(GLOBAL.TEACHER != null){
            if(GLOBAL.TEACHER.getUsername().equals(course.getTeacher().getUsername())){
                viewer = ViewerType.OwnerTeacherNormal;
            }
            else{
                viewer = ViewerType.NormalTeacher;
            }
        }
        else if(GLOBAL.STUDENT != null){
            if(course.isBoughtBy(GLOBAL.STUDENT)){
                viewer = ViewerType.OwnerStudent;
            }
            else viewer = ViewerType.NormalStudent;
        }
        else viewer = ViewerType.Admin;
    }

    private void addReview() {
        if(viewer == ViewerType.OwnerStudent || viewer == ViewerType.NormalStudent){
            if(Review.isReviewed(course, GLOBAL.STUDENT)){
                removeAddReviewBtn();
            }
        }
        else{
            VBox parent = (VBox) writeReviewBtn.getParent();
            int idx = parent.getChildren().indexOf(writeReviewBtn);
            parent.getChildren().remove(writeReviewBtn);
            parent.getChildren().add(idx, new Label("You can not review or rating this course."));
        }
        ArrayList<Review> reviewList = Review.getList(course);
        for(Review review : reviewList){
            addReviewBox(review);
        }
    }
    
    public void setViewer(ViewerType viewer){
        this.viewer = viewer;
    }

    private void makeBuyNowToApproveCourse() {
        if(course.isIsApproved()){
            buyNowButton.setText("Unapprove");
        }
        else{
            buyNowButton.setText("Approve");
        }
        buyNowButton.setOnMouseClicked((event) -> {
            if(buyNowButton.getText().equals("Approve")){
                adminCtrl.approveCourse(course.getId());
                buyNowButton.setText("Unapprove");
            }
            else{
                adminCtrl.unapproveCourse(course.getId());
                buyNowButton.setText("Approve");
            }
        });
    }

    public void setAdminView(CourseListController adminCtrl) {
        this.adminCtrl = adminCtrl;
    }

    private void addFAQ() {
        if(viewer != ViewerType.OwnerStudent && viewer != ViewerType.NormalStudent){
            VBox parent = (VBox) askQuestionBtn.getParent();
            int idx = parent.getChildren().indexOf(askQuestionBtn);
            parent.getChildren().remove(askQuestionBtn);
            parent.getChildren().add(idx, new Label("You can not ask question for this course."));
        }
        loadFAQ();
    }
    
    public void loadFAQ(){
        faqContainer.getChildren().clear();
        ArrayList<FAQ> faqList = FAQ.getList(course);
        for(FAQ faq : faqList){
            addFAQBox(faq);
        }
    }

    private void removeAskQuestionBtn() {
        ToolKit.removeNode(askQuestionBtn);
    }

    public void addFAQBox(FAQ faq) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/FAQOutputBox.fxml"));
        try {
            AnchorPane pane = loader.load();
            faqOutputBoxCtrl = loader.<FAQOutputBoxController>getController();
            faqOutputBoxCtrl.setParent(this);
            faqOutputBoxCtrl.setViewer(viewer);
            if(faq != null) faqOutputBoxCtrl.loadData(faq);
            faqContainer.getChildren().add(0, pane);
        } catch (IOException ex) {
            Logger.getLogger(CourseDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
