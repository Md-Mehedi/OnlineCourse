/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Customize;

import Course.Overflow.Global.GLOBAL;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Md Mehedi Hasan
 */
public abstract class HoverEffect {
    private Node node;
    private Node trigger;
    private Duration duration;
    private MyFadeTransition transition;
    private FadeTransition dependent;
    
    public abstract void setLocation();

    public HoverEffect(Node trigger, Node node, Duration duration, FadeTransition dependent) {
        this.trigger = trigger;
        this.node = node;
        this.duration = duration;
        this.dependent = dependent;
        transition = new MyFadeTransition(trigger, node, duration, dependent);
        
        Platform.runLater(()->{
            GLOBAL.rootPane.getChildren().add(node);
            node.setStyle(node.getStyle() + "-fx-padding: 5 0 0 0;");
        });
        trigger.setOnMouseEntered((event) -> {
            setLocation();
        });
    }
    
    public void defaultLocation(){
        Platform.runLater(()->{
            node.setLayoutX(trigger.localToScene(trigger.getBoundsInLocal()).getMinX());
            node.setLayoutY(trigger.localToScene(trigger.getBoundsInLocal()).getMaxY()-5);
        });
    }
    

    public HoverEffect(Node trigger, Node node, Duration duration) {
        this(trigger, node, duration, new FadeTransition());
    }

    public HoverEffect(Node trigger, Node node) {
        this(trigger, node, Duration.millis(50));
    }
    
    
}
