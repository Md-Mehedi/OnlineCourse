/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Layout;

import Course.Overflow.Global.GLOBAL;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Md Mehedi Hasan
 */
public class FloatingPane {
    public AnchorPane root;
    public AnchorPane backPane;
    public StackPane sPane;
    private Region region;
    public ScaleTransition showTransition;
    public ScaleTransition closeTransition;

    public FloatingPane() {
        sPane = new StackPane();
        region = new Region();
        region.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        region.setOpacity(0.5);
        sPane.getChildren().add(region);
        Platform.runLater(()->{
            backPane.getStyleClass().add("floatingPane");
            backPane.setMaxSize(backPane.getPrefWidth(), backPane.getPrefHeight());
            
            backPane.setScaleX(0);
            backPane.setScaleY(0);
            sPane.getChildren().add(backPane);
            root = GLOBAL.rootPane;
            root.getChildren().add(sPane);
            sPane.setPrefSize(GLOBAL.WIDTH, GLOBAL.HEIGHT);
            sPane.toBack();
        });
        region.setOnMouseClicked(event->{
            close();
        });
    }
    
    public void show(){
        sPane.toFront();
        showTransition = new ScaleTransition(Duration.millis(400), backPane);
        showTransition.setToX(1);
        showTransition.setToY(1);
        showTransition.play();
    }
    
    public void close(){
        closeTransition = new ScaleTransition(Duration.millis(300), backPane);
        closeTransition.setToX(0);
        closeTransition.setToY(0);
        closeTransition.play();
        closeTransition.setOnFinished((event) -> {
            sPane.toBack();
        });
    }
    
    
}
