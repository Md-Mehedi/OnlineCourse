package Course.Overflow.Course.Show;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.ToolKit;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CourseBoxLittle extends AnchorPane{
    private HBox courseDetBoxContainer;
    private VBox bottomContainer;
    private VBox container;
    private Course course;
    
    public CourseBoxLittle(Course course){
        this.course = course;
        courseDetBoxContainer = new HBox();
        bottomContainer = new VBox();
        container = new VBox(courseDetBoxContainer, bottomContainer);
        AnchorPane temp = new AnchorPane();
        temp.getStyleClass().add("courseHBoxColor");
        temp.getStyleClass().add("shadow");
        this.getChildren().addAll(temp, container);
        ToolKit.setAnchor(temp, 0, 0, 0, 0);
        ToolKit.setAnchor(container, 10, 10, 10, 10);
        
        this.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        this.getStylesheets().add(GLOBAL.COURSE_SHOW_LOCATION + "/CourseShow.css");
        this.getStyleClass().add("courseLittleBox");

        courseDetBoxContainer.getStyleClass().add("courseHBox");
//        courseDetBoxContainer.setStyle("-fx-cursor: hand;");
        bottomContainer.setStyle("-fx-spacing: 20;");
        container.setStyle("-fx-alignment: top-center;");
        
        makeCourseBox();
        resetWidth(1000);
    }

    private void makeCourseBox(){
        ImageView iv = new ImageView(ToolKit.makeImage(course.getCourseImage()));
        iv.setFitWidth(180);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        iv.getStyleClass().add("link");
        iv.setOnMouseClicked(event ->{
            GLOBAL.PAGE_CTRL.loadPage(PageName.Course);
            ((CourseDetailsController)GLOBAL.PAGE_CTRL.getController()).loadData(course);
        });
        
        Label courseName = new Label(course.getTitle());
        courseName.getStyleClass().add("title5");
        courseName.getStyleClass().add("link");
        courseName.setOnMouseClicked(event ->{
            GLOBAL.PAGE_CTRL.loadPage(PageName.Course);
            ((CourseDetailsController)GLOBAL.PAGE_CTRL.getController()).loadData(course);
        });
        
        Label instName = new Label(course.getTeacher().getFullName());
        instName.getStyleClass().add("instName");
        Label shortDesc = new Label(course.getSubTitle());
        shortDesc.getStyleClass().add("shortDesc");
        courseDetBoxContainer.getChildren().addAll(iv,new VBox(courseName,instName,shortDesc));
    }
    
    public void addData(Pane pane){
        bottomContainer.getChildren().add(pane);
        ToolKit.setAnchor(pane, 0, 0, 0, 0);
    }
    
    public void resetWidth(double value){
        setMaxWidth(value);
        bottomContainer.setMaxWidth(value);
        courseDetBoxContainer.setMaxWidth(value);
    }
}
