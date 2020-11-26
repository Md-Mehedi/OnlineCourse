/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Show.CourseDetailsController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.CoursePage;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.PurchaseHistory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CourseBoxHorizontalController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private HBox hContainer;
    @FXML
    private ImageView coursePhoto;
    @FXML
    private Label courseName;
    @FXML
    private Label courseSubTitle;
    @FXML
    private Label courseInst;
    @FXML
    private Label rating;
    @FXML
    private Rating ratingStar;
    @FXML
    private Label numOfRating;
    @FXML
    private Label numOfLecture;
    @FXML
    private Label level;
    @FXML
    private Label curPrice;
    @FXML
    private Label mainPrice;
    private Course course;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        container.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Course);
            CoursePage page = (CoursePage) GLOBAL.PAGE_CTRL.getPage();
            CourseDetailsController ctrl = page.getController();
            ctrl.loadData(course);
        });
    }    

    public void addPriceAndPurchaseDateColumn(PurchaseHistory history) {
        Label label1 = new Label("Price");
        Label label2 = new Label(ToolKit.DoubleToString(history.getCost()));
        label2.setStyle("-fx-font-weight: bold;");
        
        VBox box = new VBox(label1, label2);
        box.setStyle("-fx-alignment: center;");
        box.setMinWidth(90);
        
        hContainer.getChildren().remove(2);
        hContainer.getChildren().add(box);
        
        label1 = new Label("Purchased on");
        label2 = new Label(ToolKit.makeDateStructured(history.getTime(), "dd MMMMM, yyyy"));
        Label label3 = new Label(ToolKit.makeDateStructured(history.getTime(), "hh:mm aa"));
        label2.setStyle("-fx-font-weight: bold;");
        
        box = new VBox(label1, label2, label3);
        box.setStyle("-fx-alignment: center;");
        box.setMinWidth(190);
        
        hContainer.getChildren().add(box);
    }

    public void loadData(Course course) {
        this.course = course;
        coursePhoto.setImage(ToolKit.makeImage(course.getCourseImage()));
        courseName.setText(course.getTitle());
        courseSubTitle.setText(course.getSubTitle());
        courseInst.setText(course.getTeacher().getFullName());
        rating.setText(ToolKit.DoubleToString(course.getRating()));
        ratingStar.setRating(course.getRating());
        ratingStar.setOnMouseClicked((event) -> {
            ratingStar.setRating(course.getRating());
        });
        numOfRating.setText("(" + course.getNumOfRating() + ")");
        curPrice.setText(course.getCurrentPrice().toString());
        mainPrice.setText(course.getMainPrice().toString());
        numOfLecture.setText(course.getNumOfLectures() + " Lectures");
        
//        Platform.runLater(()->{
//            Pane pane = (Pane) container.getParent();
//            Double width = pane.getPrefWidth();
//            container.setMinWidth(width);
//            container.setMaxWidth(width);
//        });
    }
    
    public Course getCourse(){
        return course;
    }
}
