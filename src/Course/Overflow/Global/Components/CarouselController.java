/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CarouselController implements Initializable {

    @FXML
    private Label leftArrow;
    @FXML
    private HBox courseBoxContainer;
    @FXML
    private ScrollPane scrollPane;

    AnchorPane pane;
    @FXML
    private AnchorPane container;
    @FXML
    private Label rightArrow;

    private int maxShowItem;
    private int maxScrollItem;
    private boolean animationFinished;
    private Duration time;
    private boolean isFullScroll;
    private ArrayList<Course> courses;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Stop scrolling by Mouse 
        scrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaX() != 0) {
                    event.consume();
                }
            }
        });
        time = Duration.millis(500);
        isFullScroll = true;
        animationFinished = true;
        maxShowItem = 5;
        maxScrollItem = 1;

        
        scrollPane.setBackground(
              new Background(new BackgroundFill(Color.TRANSPARENT, null, null))
        );
        updateContainerSpacing();
    }
    
    private void updateContainerSpacing(){
        Platform.runLater(() -> {
            double totWidth = scrollPane.getWidth();
            double boxWidth = pane.getWidth();
            maxShowItem = (int) (totWidth / (boxWidth + 10));
            if (isFullScroll) {
                maxScrollItem = maxShowItem;
            }
            double space = totWidth - boxWidth * maxShowItem - 10;
            courseBoxContainer.setStyle("-fx-spacing: " + space / (maxShowItem - 1));
        });
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        Object src = event.getSource();
        if (src == leftArrow || src == rightArrow) {
            int element = courseBoxContainer.getChildren().size();
            if (animationFinished) {
                double totwidth = courseBoxContainer.getWidth();
                double boxWidth = pane.getWidth() + courseBoxContainer.getSpacing();
                Timeline t = new Timeline(new KeyFrame(time, new KeyValue(scrollPane.hvalueProperty(), scrollPane.getHvalue() + (src == rightArrow ? 1.0 : -1.0) * boxWidth * maxScrollItem / (totwidth - maxShowItem * boxWidth + courseBoxContainer.getSpacing()))));
                animationFinished = false;
                t.play();
                t.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        animationFinished = true;
                    }
                });
            }
        }
    }

    public void set(Duration time, int maxScrollItem) {
        this.time = time;
        this.maxScrollItem = maxScrollItem;
        isFullScroll = false;
    }

    public void set(Duration time, boolean isFullScroll) {
        this.time = time;
        this.isFullScroll = isFullScroll;
        if (isFullScroll) {
            maxScrollItem = maxShowItem;
        }
    }
    
    private void loadData(){
        try {
            for(Course course : courses){
//            for (int i = 0; i < 20; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/CourseBox.fxml"));
                pane = loader.load();
                CourseBoxController ctrl = loader.<CourseBoxController>getController();
//                ctrl.setCourseTitle("" + (i + 1));
                ctrl.setCourse(course);
                courseBoxContainer.getChildren().add(pane);
            }
        } catch (IOException ex) {
            Logger.getLogger(CarouselController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
        loadData();
    }

}
