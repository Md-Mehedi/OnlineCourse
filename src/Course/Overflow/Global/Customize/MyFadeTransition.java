/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Customize;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Md Mehedi Hasan
 */
public class MyFadeTransition {
    private Node trigger;
    private Node node;
    private Duration duration;
//    private ArrayList<FadeTransition> dependentTransition;
    private FadeTransition dependentTransition;
    
    FadeTransition show;
    FadeTransition hide;

    public MyFadeTransition(Node trigger, Node node){
        this.trigger = trigger;
        this.node = node;
        duration = new Duration(50);
        dependentTransition = new FadeTransition();
        show = new FadeTransition(duration, node);
        hide = new FadeTransition(Duration.millis(duration.toMillis()+200), node);
        
        show.setToValue(1);
        hide.setToValue(0);
        trigger.setStyle(trigger.getStyle() + "-fx-cursor: hand;");
        node.setOpacity(0);
        Platform.runLater(() -> {
            node.toBack();
            addListener();
        });
    }

    public MyFadeTransition(Node trigger, Node node, Duration duration) {
        this(trigger, node);
        this.duration = duration;
    }
    
    public MyFadeTransition(Node trigger, Node node, Duration duration, FadeTransition dependentTransition) {
        this(trigger, node, duration);
        this.dependentTransition = dependentTransition;
    }

    private void addListener() {
        hide.setOnFinished((event) -> {
            node.toBack();
        });
        trigger.setOnMouseMoved((event) -> {
            node.toFront();
            show.play();
        });
        trigger.setOnMouseExited((event) -> {
            hide.play();
        });
        node.setOnMouseMoved((event) -> {
            hide.stop();
            dependentTransition.stop();
        });
        node.setOnMouseExited((event) -> {
            hide.play();
            dependentTransition.play();
        });
    }

    public FadeTransition getShow() {
        return show;
    }

    public FadeTransition getHide() {
        return hide;
    }
}
