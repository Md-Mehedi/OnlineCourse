/*
 * To change this license header, choose License Headers in Project Properties.
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

    public FloatingPane() {
        sPane = new StackPane();
        region = new Region();
        region.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        region.setOpacity(0.5);
        sPane.getChildren().add(region);
        Platform.runLater(()->{
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
        ScaleTransition t1 = new ScaleTransition(Duration.millis(400), backPane);
        t1.setToX(1);
        t1.setToY(1);
        t1.play();
    }
    
    public void close(){
        ScaleTransition t1 = new ScaleTransition(Duration.millis(300), backPane);
        t1.setToX(0);
        t1.setToY(0);
        t1.play();
        t1.setOnFinished((event) -> {
            sPane.toBack();
        });
    }
    
    
}
