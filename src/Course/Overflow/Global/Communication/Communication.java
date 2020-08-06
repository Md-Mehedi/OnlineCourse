/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Global.GLOBAL;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Communication {
    AnchorPane root;
    AnchorPane pane;
    VBox container;
    VBox bottomContainer;
    
    public Communication(){
        root = new AnchorPane();
        container = new VBox();
        bottomContainer = new VBox();
        container.getChildren().add(bottomContainer);
        bottomContainer.setStyle("-fx-spacing: 20;");
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        root.getStylesheets().add(GLOBAL.COMMUNICATION_LOCATION + "/Communication.css");
        
        
        pane = getCourseNameBox();
        VBox box = new VBox(pane,container);
        box.getStyleClass().add("comMainBox");
        root.getChildren().add(box);
    }
    
    public AnchorPane getCourseNameBox(){
        ImageView iv = new ImageView();
        iv.setFitWidth(180);
        iv.setFitHeight(120);
        Label courseName = new Label("Course name");
        courseName.getStyleClass().add("title5");
        Label instName = new Label("Instructor name");
        instName.getStyleClass().add("instName");
        Label shortDesc = new Label("Short description");
        shortDesc.getStyleClass().add("shortDesc");
        AnchorPane box = new AnchorPane(new HBox(iv,new VBox(courseName,instName,shortDesc)));
        box.getStyleClass().add("courseHBox");
        return box;
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}
