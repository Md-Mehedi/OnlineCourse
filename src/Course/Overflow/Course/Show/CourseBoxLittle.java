/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course.Show;

import Course.Overflow.Global.GLOBAL;
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
    private VBox bottomContaienr;
    private VBox container;
    
    public CourseBoxLittle(){
        courseDetBoxContainer = new HBox();
        bottomContaienr = new VBox();
        container = new VBox(courseDetBoxContainer,bottomContaienr);
        this.getChildren().add(container);
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        
        this.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        this.getStylesheets().add(GLOBAL.COURSE_SHOW_LOCATION + "/CourseShow.css");
        this.getStyleClass().add("courseLittleBox");
        courseDetBoxContainer.getStyleClass().add("courseHBox");
        bottomContaienr.setStyle("-fx-spacing: 20;");
        container.setStyle("-fx-alignment: top-center;");
        
        makeCourseBox();
        resetWidth(1000);
    }

    private void makeCourseBox(){
        ImageView iv = new ImageView();
        iv.setFitWidth(180);
        iv.setFitHeight(120);
        Label courseName = new Label("Course name");
        courseName.getStyleClass().add("title5");
        Label instName = new Label("Instructor name");
        instName.getStyleClass().add("instName");
        Label shortDesc = new Label("Short description");
        shortDesc.getStyleClass().add("shortDesc");
        courseDetBoxContainer.getChildren().addAll(iv,new VBox(courseName,instName,shortDesc));
    }
    
    public void addData(Pane pane){
        bottomContaienr.getChildren().add(pane);
    }
    
    public void resetWidth(double value){
        setMaxWidth(value);
        bottomContaienr.setMaxWidth(value);
        courseDetBoxContainer.setMaxWidth(value);
    }
}
